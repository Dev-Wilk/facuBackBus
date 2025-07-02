# Estágio de build
FROM node:20 AS build
WORKDIR /app

# Copia os arquivos de dependências primeiro (para aproveitar o cache)
COPY package*.json ./
# Instala as dependências
RUN npm install

# Copia o restante do código-fonte
COPY . .
# Constrói a aplicação para produção
RUN npm run build

# Estágio de execução (imagem mais leve)
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Remove os arquivos padrão do nginx
RUN rm -rf ./*

# Copia apenas os arquivos de build do estágio anterior
COPY --from=build /app/dist .

# Expõe a porta padrão do Nginx
# Utilizamos a porta 5173 conforme configurado no frontend
EXPOSE 5173

# Comando para executar o servidor nginx
CMD ["nginx", "-g", "daemon off;"]