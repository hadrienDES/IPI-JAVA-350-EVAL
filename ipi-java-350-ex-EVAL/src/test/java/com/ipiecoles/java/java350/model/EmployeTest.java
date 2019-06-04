package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    @Test
    public void GetNombreAnneeAncienneteDateEmbaucheNull(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(null);

        //When
        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, (int)nbAnneeAnciennete);
    }

    @Test
    public void getNombreAnneeAncienneteNminus2(){

        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(2, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNplus2(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @ParameterizedTest(name = "performance {0}, Employé de matricule {1},  ancienneté {2}, tps partiel : {3} => prime : {4}")
    @CsvSource({
            "1, 'T12345', 0, 1.0, 1000.0",
            "2, 'T12345', 2, 1.0, 2000.0",
            "1, 'M12345', 0, 1.0, 1500.0",
            "2, 'M12345', 2, 1.0, 2500.0"
    })
    void getPrimeAnnuelle(Integer performance, String matricule, Long nbyearsAnciennete, Double tempsPartiel, Double primeAnnuelle){

        //Given
        Employe employe = new Employe("JACK", "JEAN", matricule, LocalDate.now().minusYears(nbyearsAnciennete), Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertEquals(primeAnnuelle, prime);
    }



    @Test
    public void AugmenterSalaireNoNullNoInf() throws EmployeException {

        //Given
        Employe e = new Employe();
        e.setSalaire(1200.0);       //noNull
        Integer pourcentage = 15;   //noInf

        //When
        e.augmenterSalaire(pourcentage);
        Double augmentationSalaire = e.getSalaire();

        //Then
        Assertions.assertEquals(augmentationSalaire, (Double)1200.0);
    }

    @Test
    public void AugmenterSalaireNullNoInf() throws EmployeException {

        //Given
        Employe e = new Employe();
        e.setSalaire(0D);       //Null
        Integer pourcentage = 15;   //noInf

        //When
        e.augmenterSalaire(pourcentage);
        Double augmentationSalaire = e.getSalaire();

        //Then
        EmployeException employeException = Assertions.assertThrows(EmployeException.class, () -> e.augmenterSalaire(pourcentage));
        Assertions.assertEquals("salaire égal à 0 erreur", employeException.getMessage());

    }

    @Test
    public void AugmenterSalaireNoNullButInf() throws EmployeException {

        //Given
        Employe e = new Employe();
        e.setSalaire(1200.0);       //Null
        Integer pourcentage = -15;   //noInf

        //When
        e.augmenterSalaire(pourcentage);
        Double augmentationSalaire = e.getSalaire();

        //Then
        EmployeException employeException = Assertions.assertThrows(EmployeException.class, () -> e.augmenterSalaire(pourcentage));
        Assertions.assertEquals("pourcentage inf à 0 erreur", employeException.getMessage());
    }

    /* ENNONCE
    2019 : l'année est non bissextile, a débuté un mardi et il y a 10 jours fériés ne tombant pas le week-end.
    2021 : l'année est non bissextile, a débuté un vendredi et il y a 7 jours fériés ne tombant pas le week-end.
    2022 : l'année est non bissextile, a débuté un samedi et il y a 7 jours fériés ne tombant pas le week-end.
    2032 : l'année est bissextile, a débuté un jeudi et il y a 7 jours fériés ne tombant pas le week-end.
    */

    @ParameterizedTest
    @CsvSource({
            "2019-01-01, 10",
            "2021-01-01, 7",
            "2022-01-01, 7",
            "2032-01-01, 7"
    })

    public void GetNbRtt(LocalDate date,Integer nbRttExpected){
        //GIVEN
        Employe e = new Employe();

        //WHEN
        Integer nbRtt = e.getNbRtt(date);

        //GIVEN
        Assertions.assertEquals(nbRtt, nbRttExpected);
        System.out.println("Nombre de RTT : " + nbRtt);
    }

}