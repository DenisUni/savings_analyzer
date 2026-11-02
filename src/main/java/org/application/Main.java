package org.application;

import org.infrastructure.database.DatabaseManagement;
import org.presentation.CLI;

//https://martinfowler.com/bliki/PresentationDomainDataLayering.html

public class Main {
    public static void main(String[] args) {

        CLI.startPage();
        System.out.println("END!");
    }
}