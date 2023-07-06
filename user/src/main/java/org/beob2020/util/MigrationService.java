package org.beob2020.util;

import io.quarkus.liquibase.LiquibaseFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import liquibase.Liquibase;
import liquibase.changelog.ChangeSetStatus;

import java.util.List;

@ApplicationScoped
public class MigrationService {

    @Inject
    LiquibaseFactory liquibaseFactory;

    public void checkMigration() {
        try (Liquibase liquibase = liquibaseFactory.createLiquibase()) {
            liquibase.dropAll();
            liquibase.validate();
            liquibase.update(liquibaseFactory.createContexts(), liquibaseFactory.createLabels());
            List<ChangeSetStatus> status = liquibase.getChangeSetStatuses(liquibaseFactory.createContexts(), liquibaseFactory.createLabels());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
