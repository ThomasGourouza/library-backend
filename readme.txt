# one line
rm src/main/resources/openlibrary-truststore.jks && \
rm src/main/resources/mymemory-trust.jks && \
openssl s_client -showcerts -connect openlibrary.org:443 </dev/null 2>/dev/null | \
  awk '/BEGIN CERTIFICATE/,/END CERTIFICATE/ { print }' > openlibrary-chain.pem && \
openssl s_client -connect api.mymemory.translated.net:443 -showcerts </dev/null | \
  openssl x509 -outform PEM > corp-or-site-ca.pem && \
keytool -importcert -alias openlibrary-ca \
  -file openlibrary-chain.pem \
  -keystore src/main/resources/openlibrary-truststore.jks \
  -storepass openlibrary123 -noprompt && \
keytool -importcert -alias mymemory-ca \
  -file corp-or-site-ca.pem \
  -keystore src/main/resources/mymemory-trust.jks \
  -storepass mymemory123 -noprompt && \
rm openlibrary-chain.pem && \
rm corp-or-site-ca.pem && \
rm -f src/main/resources/wikidata-truststore.jks && \
openssl s_client -showcerts -connect query.wikidata.org:443 </dev/null 2>/dev/null \
  | awk '/BEGIN CERTIFICATE/,/END CERTIFICATE/ { print }' > wikidata-chain.pem && \
awk '
  /BEGIN CERTIFICATE/ { n++; fn=sprintf("cert-%02d.pem", n) }
  { print > fn }
  /END CERTIFICATE/ { close(fn) }
' wikidata-chain.pem && \
i=0
for cert in cert-*.pem; do
  alias="wikidata-ca-$i"
  keytool -importcert \
    -alias "$alias" \
    -file "$cert" \
    -keystore src/main/resources/wikidata-truststore.jks \
    -storepass wikidata123 -noprompt
  i=$((i+1))
done && \
rm -f wikidata-chain.pem cert-*.pem

---

# get the server chain
openssl s_client -showcerts -connect openlibrary.org:443 </dev/null 2>/dev/null | \
  awk '/BEGIN CERTIFICATE/,/END CERTIFICATE/ { print }' > openlibrary-chain.pem

# import the *root or intermediate CA* you need (better: the root)
keytool -importcert -alias openlibrary-ca \
  -file openlibrary-chain.pem \
  -keystore src/main/resources/openlibrary-truststore.jks \
  -storepass openlibrary123 -noprompt

# one line
rm src/main/resources/openlibrary-truststore.jks && \
openssl s_client -showcerts -connect openlibrary.org:443 </dev/null 2>/dev/null | \
  awk '/BEGIN CERTIFICATE/,/END CERTIFICATE/ { print }' > openlibrary-chain.pem && \
keytool -importcert -alias openlibrary-ca \
  -file openlibrary-chain.pem \
  -keystore src/main/resources/openlibrary-truststore.jks \
  -storepass openlibrary123 -noprompt && \
rm openlibrary-chain.pem

-------

# 1 Grab the cert chain actually presented to you (proxy or site)
openssl s_client -connect api.mymemory.translated.net:443 -showcerts </dev/null | \
  openssl x509 -outform PEM > corp-or-site-ca.pem

# 2 Import into a JKS (put JKS under src/main/resources/)
keytool -importcert -alias mymemory-ca \
  -file corp-or-site-ca.pem \
  -keystore src/main/resources/mymemory-trust.jks \
  -storepass mymemory123 -noprompt

# one line
rm src/main/resources/mymemory-trust.jks && \
openssl s_client -connect api.mymemory.translated.net:443 -showcerts </dev/null | \
  openssl x509 -outform PEM > corp-or-site-ca.pem && \
keytool -importcert -alias mymemory-ca \
  -file corp-or-site-ca.pem \
  -keystore src/main/resources/mymemory-trust.jks \
  -storepass mymemory123 -noprompt && \
rm corp-or-site-ca.pem
