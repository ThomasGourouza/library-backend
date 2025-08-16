FROM python:3.11-slim

# system deps for curl
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*

# python deps
RUN pip install --no-cache-dir fastapi uvicorn transformers==4.43.3 torch==2.3.1

WORKDIR /app
COPY server.py /app/server.py

EXPOSE 8000
CMD ["uvicorn", "server:app", "--host", "0.0.0.0", "--port", "8000", "--workers", "1"]
