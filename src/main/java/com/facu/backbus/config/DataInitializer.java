package com.facu.backbus.config;

import com.facu.backbus.model.User;
import com.facu.backbus.model.enums.UserType;
import com.facu.backbus.repository.UserRepository;
import com.facu.backbus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe de configuração responsável por inicializar dados no sistema.
 * Cria usuários padrão (1 gerente e 4 atendentes) quando a aplicação é iniciada.
 */
@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            try {
                logger.info("Verificando se existem usuários no sistema...");
                // Verifica se já existem usuários no sistema
                if (userRepository.count() == 0) {
                    logger.info("Nenhum usuário encontrado. Inicializando usuários padrão...");
                    
                    try {
                        // Cria o usuário gerente
                        User gerente = new User();
                        gerente.setFullName("Gerente");
                        gerente.setLogin("gerente");
                        gerente.setPassword("senha123!");
                        gerente.setUserType(UserType.GERENTE);
                        
                        logger.info("Salvando usuário gerente: {}", gerente.getLogin());
                        User gerenteSalvo = userService.save(gerente);
                        logger.info("Usuário gerente salvo com ID: {}", gerenteSalvo.getId());
                        
                        // Cria os usuários atendentes
                        User joao = new User();
                        joao.setFullName("João");
                        joao.setLogin("joao");
                        joao.setPassword("senha123!");
                        joao.setUserType(UserType.ATENDENTE);
                        
                        logger.info("Salvando usuário atendente: {}", joao.getLogin());
                        User joaoSalvo = userService.save(joao);
                        logger.info("Usuário atendente salvo com ID: {}", joaoSalvo.getId());
                        
                        User marcos = new User();
                        marcos.setFullName("Marcos");
                        marcos.setLogin("marcos");
                        marcos.setPassword("senha123!");
                        marcos.setUserType(UserType.ATENDENTE);
                        
                        logger.info("Salvando usuário atendente: {}", marcos.getLogin());
                        User marcosSalvo = userService.save(marcos);
                        logger.info("Usuário atendente salvo com ID: {}", marcosSalvo.getId());
                        
                        User alberto = new User();
                        alberto.setFullName("Alberto");
                        alberto.setLogin("alberto");
                        alberto.setPassword("senha123!");
                        alberto.setUserType(UserType.ATENDENTE);
                        
                        logger.info("Salvando usuário atendente: {}", alberto.getLogin());
                        User albertoSalvo = userService.save(alberto);
                        logger.info("Usuário atendente salvo com ID: {}", albertoSalvo.getId());
                        
                        User luiz = new User();
                        luiz.setFullName("Luiz");
                        luiz.setLogin("luiz");
                        luiz.setPassword("senha123!");
                        luiz.setUserType(UserType.ATENDENTE);
                        
                        logger.info("Salvando usuário atendente: {}", luiz.getLogin());
                        User luizSalvo = userService.save(luiz);
                        logger.info("Usuário atendente salvo com ID: {}", luizSalvo.getId());
                        
                        logger.info("Todos os usuários padrão foram criados com sucesso!");
                    } catch (Exception e) {
                        logger.error("Erro ao salvar usuários: {}", e.getMessage(), e);
                        throw e; // Re-throw para garantir que o erro seja visível
                    }
                } else {
                    logger.info("Banco de dados já possui usuários. Pulando inicialização de dados.");
                }
            } catch (Exception e) {
                logger.error("Erro durante a inicialização de dados: {}", e.getMessage(), e);
                throw e; // Re-throw para garantir que o erro seja visível
            }
        };
    }
}
