from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from transformers import AutoTokenizer, AutoModelForSeq2SeqLM
import torch

MODEL_NAME = "facebook/nllb-200-distilled-600M"  # ~600M params (good balance)
device = "cuda" if torch.cuda.is_available() else "cpu"

tokenizer = AutoTokenizer.from_pretrained(MODEL_NAME)
model = AutoModelForSeq2SeqLM.from_pretrained(MODEL_NAME).to(device)

app = FastAPI()

class TranslateReq(BaseModel):
    text: str
    source: str  # e.g. "eng_Latn"
    target: str  # e.g. "fra_Latn"

@app.get("/health")
def health():
    return {"status": "ok"}

@app.post("/translate")
def translate(req: TranslateReq):
    try:
        # (Optional) Validate language codes exist in tokenizer
        if req.target not in tokenizer.lang_code_to_id:
            raise HTTPException(400, f"Unknown target lang code: {req.target}")
        # Note: source code is not strictly required for generation, but you can validate similarly

        inputs = tokenizer(req.text, return_tensors="pt").to(device)
        outputs = model.generate(
            **inputs,
            forced_bos_token_id=tokenizer.lang_code_to_id[req.target],
            max_new_tokens=400,
            num_beams=3
        )
        text = tokenizer.batch_decode(outputs, skip_special_tokens=True)[0]
        return {"translation": text}
    except Exception as e:
        raise HTTPException(500, str(e))
