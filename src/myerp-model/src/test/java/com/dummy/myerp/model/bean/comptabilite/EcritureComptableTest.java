package com.dummy.myerp.model.bean.comptabilite;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;


public class EcritureComptableTest {

	EcritureComptable vEcriture = new EcritureComptable();
	 
    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                                     .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                                                                    vLibelle,
                                                                    vDebit, vCredit);
        return vRetour;
    }

    @Test
    public void isEquilibree() {
        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        Assert.assertFalse(vEcriture.toString(), vEcriture.isEquilibree());
    }
    
    @Test
  public void getTotalDebit() {
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        assertEquals(341, vEcriture.getTotalDebit().intValue());
    }
    
    @Test
  public void getTotalCredit() {
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        assertEquals(33, vEcriture.getTotalCredit().intValue());
    }
    
    @Test
   public void getReference() {
        vEcriture.setReference("BQ-2018/00005");
        Assert.assertTrue("La ref ne respecte pas le format \"XX-AAAA/#####\"", vEcriture.getReference().matches("[A-Z]{1,5}-\\d{4}/\\d{5}"));
    }
	  @Test
   public void referenceCodeEqualJournalCode() {
        vEcriture.setJournal(new JournalComptable("BQ", "Banque"));
        vEcriture.setReference("BQ-2016/00003");
        assertEquals(vEcriture.getReference().substring(0, 2), vEcriture.getJournal().getCode());
    }

	  @Test
	    public void GettersTest() {
	        String ref = "reference";
	        vEcriture.setLibelle(ref);

	        Assert.assertTrue(vEcriture.getLibelle().equals("reference"));

	        vEcriture.setDate(new Date());
	        Assert.assertTrue(vEcriture.getDate().equals(new Date()));

	        vEcriture.setReference("reference 1");
	        Assert.assertTrue(vEcriture.getReference().equals("reference 1"));

	        vEcriture.setId(1);
	        Assert.assertTrue(vEcriture.getId().equals(1));
	        
	        vEcriture.setLibelle("libellé");
	        Assert.assertTrue(vEcriture.getLibelle().equals("libellé"));
	        
	        
	    }

}
