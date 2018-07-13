package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class CompteComptableTest {

    private static CompteComptable vCompte = new CompteComptable();;
    private static List<CompteComptable> vList = new ArrayList<>(0);

   

    @Test
   public void getByNumero() {
        vCompte.setNumero(401);
        vCompte.setLibelle("Fournisseurs");
        vList = new ArrayList<>(0);
        vList.add(vCompte);
        vList.add(new CompteComptable(411, "Clients"));
        Assert.assertEquals(CompteComptable.getByNumero(vList, 401), vCompte);
    }
     @Test
    public void GettersTest() {
    	vCompte.setLibelle("pLibelle");
        Assert.assertTrue(vCompte.getLibelle().equals("pLibelle"));

        vCompte.setNumero(12);
        Assert.assertTrue(vCompte.getNumero().equals(12));
        
    }
}
