FROM python:3.11-slim

# System deps
RUN apt-get update && apt-get install -y --no-install-recommends \
    git && rm -rf /var/lib/apt/lists/*

# Python deps
RUN pip install --no-cache-dir torch transformers fastapi uvicorn[standard] sentencepiece

# (Optional) pre-download weights so the container is warm on first request
RUN python - <<'PY'
from transformers import AutoTokenizer, AutoModelForSeq2SeqLM
m = "facebook/nllb-200-distilled-600M"
AutoTokenizer.from_pretrained(m)
AutoModelForSeq2SeqLM.from_pretrained(m)
PY

WORKDIR /app
COPY server.py /app/server.py
EXPOSE 8000
CMD ["uvicorn", "server:app", "--host", "0.0.0.0", "--port", "8000"]
