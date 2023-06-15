package com.axon.udemy

import io.holixon.axon.testcontainer.AxonServerContainer
import io.holixon.axon.testcontainer.spring.addDynamicProperties
import mu.KLogging
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.containers.wait.strategy.WaitStrategy
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Duration
import java.util.*

@SpringBootTest(classes = [UdemyApplicationTests::class], webEnvironment = WebEnvironment.NONE)
@Testcontainers
@Disabled("seems to be an issue to run kotlin and java test app in the same scope on github")
internal class AxonServerContainerKotlinITest {
    companion object : KLogging() {

        @Container
        val axon = AxonServerContainer.builder()
            .enableDevMode()
            .build().apply {
//                Wait.forHealthcheck().withStartupTimeout(Duration.ofSeconds(180))
                // https://java.testcontainers.org/features/startup_and_waits/#waiting-for-200-ok
//                Wait.withStartupTimeout(Duration.ofSeconds(180))
                logger.info { "------ $this" }
            }

        @JvmStatic
        @DynamicPropertySource
        fun axonProperties(registry: DynamicPropertyRegistry) = axon.addDynamicProperties(registry)

        @JvmStatic
        @AfterAll
        internal fun tearDown() {
            axon.stop()
        }
    }

    @Autowired
    private lateinit var commandGateway: CommandGateway

    @Autowired
    private lateinit var queryGateway: QueryGateway

    @Test
    fun `start axon server, run cmd, evt and query`() {
//    val accountId = UUID.randomUUID().toString()
//
//    commandGateway.sendAndWait<Any>(KotlinTestApplication.CreateBankAccountCommand(accountId = accountId, initialBalance = 100))
//
//    val account = queryGateway.query(
//      KotlinTestApplication.FindBankAccountById(accountId),
//      ResponseTypes.optionalInstanceOf(KotlinTestApplication.BankAccountDto::class.java)
//    ).join().orElseThrow()
//
//    assertThat(account.accountId).isEqualTo(accountId)
//    assertThat(account.balance).isEqualTo(100)
    }
}
