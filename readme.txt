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