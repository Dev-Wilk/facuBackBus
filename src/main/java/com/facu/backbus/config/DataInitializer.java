package com.facu.backbus.config;

import com.facu.backbus.model.Bus;
import com.facu.backbus.model.Driver;
import com.facu.backbus.model.User;
import com.facu.backbus.model.enums.BusStatus;
import com.facu.backbus.model.enums.DriverStatus;
import com.facu.backbus.model.enums.UserType;
import com.facu.backbus.repository.BusRepository;
import com.facu.backbus.repository.DriverRepository;
import com.facu.backbus.repository.UserRepository;
import com.facu.backbus.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    
    @Autowired
    private BusRepository busRepository;
    
    @Autowired
    private DriverRepository driverRepository;

    /**
     * Método auxiliar para salvar usuários diretamente no repositório,
     * aplicando o hash da senha manualmente para contornar problemas de validação
     */
    private User saveUserDirectly(User user) {
        // Aplicar hash na senha manualmente
        String hashedPassword = com.facu.backbus.util.HashUtil.gerarHashSHA256(user.getPassword());
        user.setPassword(hashedPassword);
        
        // Salvar diretamente no repositório, contornando a validação
        return userRepository.save(user);
    }
    
    @Bean
    public CommandLineRunner initData() {
        return args -> {
            try {
                // Inicialização de usuários
                logger.info("Verificando se existem usuários no sistema...");
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
                        User gerenteSalvo = saveUserDirectly(gerente);
                        logger.info("Usuário gerente salvo com ID: {}", gerenteSalvo.getId());
                        
                        // Cria os usuários atendentes
                        User joao = new User();
                        joao.setFullName("João");
                        joao.setLogin("joao");
                        joao.setPassword("senha123!");
                        joao.setUserType(UserType.ATENDENTE);
                        
                        logger.info("Salvando usuário atendente: {}", joao.getLogin());
                        User joaoSalvo = saveUserDirectly(joao);
                        logger.info("Usuário atendente salvo com ID: {}", joaoSalvo.getId());
                        
                        User marcos = new User();
                        marcos.setFullName("Marcos");
                        marcos.setLogin("marcos");
                        marcos.setPassword("senha123!");
                        marcos.setUserType(UserType.ATENDENTE);
                        
                        logger.info("Salvando usuário atendente: {}", marcos.getLogin());
                        User marcosSalvo = saveUserDirectly(marcos);
                        logger.info("Usuário atendente salvo com ID: {}", marcosSalvo.getId());
                        
                        User alberto = new User();
                        alberto.setFullName("Alberto");
                        alberto.setLogin("alberto");
                        alberto.setPassword("senha123!");
                        alberto.setUserType(UserType.ATENDENTE);
                        
                        logger.info("Salvando usuário atendente: {}", alberto.getLogin());
                        User albertoSalvo = saveUserDirectly(alberto);
                        logger.info("Usuário atendente salvo com ID: {}", albertoSalvo.getId());
                        
                        User luiz = new User();
                        luiz.setFullName("Luiz");
                        luiz.setLogin("luiz");
                        luiz.setPassword("senha123!");
                        luiz.setUserType(UserType.ATENDENTE);
                        
                        logger.info("Salvando usuário atendente: {}", luiz.getLogin());
                        User luizSalvo = saveUserDirectly(luiz);
                        logger.info("Usuário atendente salvo com ID: {}", luizSalvo.getId());
                        
                        logger.info("Todos os usuários padrão foram criados com sucesso!");
                    } catch (Exception e) {
                        logger.error("Erro ao salvar usuários: {}", e.getMessage(), e);
                        throw e; // Re-throw para garantir que o erro seja visível
                    }
                } else {
                    logger.info("Banco de dados já possui usuários. Pulando inicialização de usuários.");
                }
                
                // Inicialização de ônibus (independente dos usuários)
                logger.info("Verificando se existem ônibus no sistema...");
                if (busRepository.count() == 0) {
                    logger.info("Nenhum ônibus encontrado. Inicializando ônibus padrão...");
                    
                    try {
                        // Cria os ônibus padrão
                        Bus bus1 = new Bus();
                        bus1.setPlate("ABC1234"); 
                        bus1.setMaxCapacity(40);
                        bus1.setStatus(BusStatus.AVAILABLE);
                        
                        logger.info("Salvando ônibus: {}", bus1.getPlate());
                        Bus bus1Salvo = busRepository.save(bus1);
                        logger.info("Ônibus salvo com ID: {}", bus1Salvo.getId());
                        
                        Bus bus2 = new Bus();
                        bus2.setPlate("XYZ9876"); 
                        bus2.setMaxCapacity(50);
                        bus2.setStatus(BusStatus.AVAILABLE);
                        
                        logger.info("Salvando ônibus: {}", bus2.getPlate());
                        Bus bus2Salvo = busRepository.save(bus2);
                        logger.info("Ônibus salvo com ID: {}", bus2Salvo.getId());
                        
                        logger.info("Todos os ônibus padrão foram criados com sucesso!");
                    } catch (Exception e) {
                        logger.error("Erro ao salvar ônibus: {}", e.getMessage(), e);
                        throw e;
                    }
                } else {
                    logger.info("Banco de dados já possui ônibus. Pulando inicialização de ônibus.");
                }
                
                // Inicialização de motoristas (independente dos usuários e ônibus)
                logger.info("Verificando se existem motoristas no sistema...");
                if (driverRepository.count() == 0) {
                    logger.info("Nenhum motorista encontrado. Inicializando motoristas padrão...");
                    
                    try {
                        // Cria os motoristas padrão
                        Driver driver1 = new Driver();
                        driver1.setFullName("José da Silva");
                        driver1.setIdentificationNumber("123456789");
                        driver1.setContact("12345678901");
                        driver1.setStatus(DriverStatus.AVAILABLE);
                        
                        logger.info("Salvando motorista: {}", driver1.getFullName());
                        Driver driver1Salvo = driverRepository.save(driver1);
                        logger.info("Motorista salvo com ID: {}", driver1Salvo.getId());
                        
                        Driver driver2 = new Driver();
                        driver2.setFullName("Maria Oliveira");
                        driver2.setIdentificationNumber("987654321");
                        driver2.setContact("21098765432");
                        driver2.setStatus(DriverStatus.AVAILABLE);
                        
                        logger.info("Salvando motorista: {}", driver2.getFullName());
                        Driver driver2Salvo = driverRepository.save(driver2);
                        logger.info("Motorista salvo com ID: {}", driver2Salvo.getId());
                        
                        logger.info("Todos os motoristas padrão foram criados com sucesso!");
                    } catch (Exception e) {
                        logger.error("Erro ao salvar motoristas: {}", e.getMessage(), e);
                        throw e;
                    }
                } else {
                    logger.info("Banco de dados já possui motoristas. Pulando inicialização de motoristas.");
                }
            } catch (Exception e) {
                logger.error("Erro durante a inicialização de dados: {}", e.getMessage(), e);
                throw e; 
            }
        };
    }
}
