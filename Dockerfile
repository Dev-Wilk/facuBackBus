# Imagem base com a versão
FROM node:20
#Define o diretório de trabalho dentro do container
WORKDIR /app
# Copia os arquivos de despendências primeiro (para aproveitar o cache)
COPY package*.json ./
# instala as dependências
RUN npm install
# Copia o restante do código-fonte
COPY . .
#Expõe a porta padrão (React ou Next.js geralmente usem 3000)
EXPOSE 5173
#Comando padrão pao iniciar o container
CMD ["npm","run","dev"]