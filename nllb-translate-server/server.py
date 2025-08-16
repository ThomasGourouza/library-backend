from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from transformers import AutoTokenizer, AutoModelForSeq2SeqLM
import torch
import os

MODEL_NAME = os.getenv("MODEL_ID", "facebook/nllb-200-distilled-600M")

app = FastAPI()

# Load once at startup
tokenizer = AutoTokenizer.from_pretrained(MODEL_NAME, use_fast=True)
model = AutoModelForSeq2SeqLM.from_pretrained(MODEL_NAME)
model.eval()

class TranslateReq(BaseModel):
    text: str
    source: str  # e.g. "eng_Latn"
    target: str  # e.g. "fra_Latn"

@app.get("/health")
def health():
    return {"status": "ok"}

def resolve_forced_bos_id(tgt_code: str):
    """
    Resolve the BOS language token id in a tokenizer-version-agnostic way.
    """
    # Preferred path for many versions
    if hasattr(tokenizer, "lang_code_to_id") and isinstance(tokenizer.lang_code_to_id, dict):
        tid = tokenizer.lang_code_to_id.get(tgt_code)
        if tid is not None:
            return tid

    # Robust fallback: get the language token string then convert to id
    if hasattr(tokenizer, "get_lang_id"):
        lang_token = tokenizer.get_lang_id(tgt_code)  # e.g. "__fra_Latn__"
        if lang_token:
            tid = tokenizer.convert_tokens_to_ids(lang_token)
            if tid is not None and tid != tokenizer.unk_token_id:
                return tid

    # Last-resort attempt (often not needed, but kept as fallback)
    try:
        tid = tokenizer.convert_tokens_to_ids(tgt_code)
        if tid is not None and tid != tokenizer.unk_token_id:
            return tid
    except Exception:
        pass

    return None

@torch.inference_mode()
@app.post("/translate")
def translate(req: TranslateReq):
    # Validate input
    if not req.text.strip():
        raise HTTPException(status_code=400, detail="Empty text")
    if not req.source or not req.target:
        raise HTTPException(status_code=400, detail="Both 'source' and 'target' are required")

    # Set source language if tokenizer supports it
    if hasattr(tokenizer, "src_lang"):
        tokenizer.src_lang = req.source

    # Prepare inputs once
    inputs = tokenizer(req.text, return_tensors="pt")

    # Figure out the forced BOS token id for the target language
    forced_bos_token_id = resolve_forced_bos_id(req.target)
    if forced_bos_token_id is None:
        raise HTTPException(status_code=400, detail=f"Unknown/unsupported target language: {req.target}")

    # Generate
    outputs = model.generate(
        **inputs,
        forced_bos_token_id=forced_bos_token_id,  # <-- fixed name
        max_new_tokens=128,
        num_beams=1
    )
    text = tokenizer.batch_decode(outputs, skip_special_tokens=True)[0]
    return {"translation": text}
