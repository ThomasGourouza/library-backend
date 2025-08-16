# get the server chain
openssl s_client -showcerts -connect openlibrary.org:443 </dev/null 2>/dev/null | \
  awk '/BEGIN CERTIFICATE/,/END CERTIFICATE/ { print }' > openlibrary-chain.pem

# import the *root or intermediate CA* you need (better: the root)
keytool -importcert -noprompt \
  -alias openlibrary-ca \
  -file openlibrary-chain.pem \
  -keystore openlibrary-truststore.jks \
  -storepass openlibrary123

# put it under resources



### nllb translation server

#(Use --build only when you changed something in the build context (e.g., Dockerfile, server.py, requirements.txt, etc.))
docker-compose up -d --build nllb

#(If nothing or only docker-compose.yml changed)
docker-compose up -d

#Wait for healthy:
docker ps --filter name=nllb-translate

curl http://localhost:18000/health

#Test translate:
curl -H "Content-Type: application/json" \
  -d '{"text":"Hello","source":"eng_Latn","target":"fra_Latn"}' \
  http://localhost:18000/translate
