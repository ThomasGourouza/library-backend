Library Backend App

docker compose up -d
watch -n 2 'docker ps --format "table {{.Names}}\t{{.Status}}" | grep library'
