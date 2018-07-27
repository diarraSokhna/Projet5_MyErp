package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.metadata.GenericTableMetaDataProvider;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testbusiness.business.BusinessTestCase;

public class ComptabiliteManagerImplTest extends BusinessTestCase {

	private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
	private ComptabiliteManager managerIntegration = getBusinessProxy().getComptabiliteManager();
	EcritureComptable vEcritureComptable = new EcritureComptable();

	@Test
	public void getListCompteComptable() {
		List<CompteComptable> vList = managerIntegration.getListCompteComptable();
		Assert.assertEquals(7, vList.size());
	}
	// ==================== JournalComptable - GET ====================

	@Test
	public void getListJournalComptable() {
		List<JournalComptable> vList = managerIntegration.getListJournalComptable();
		Assert.assertEquals(4, vList.size());
	}

	// ==================== EcritureComptable - GET ====================

	@Test
	public void getListEcritureComptable() {
		List<EcritureComptable> vList = managerIntegration.getListEcritureComptable();
		Assert.assertEquals(5, vList.size());
	}

	@Test
	public void getEcritureComptable() throws NotFoundException {
		vEcritureComptable = manager.getEcritureComptable(-3);
		Assert.assertEquals("BQ-2016/00003", vEcritureComptable.getReference());

	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnit() throws Exception {
		Date vCurrentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String vCurrentYear = sdf.format(vCurrentDate);
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(vCurrentDate);
		vEcritureComptable.setLibelle("Libelle");
		vEcritureComptable.setReference("AC-" + vCurrentYear + "/00001");
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
		manager.checkEcritureComptableUnit(vEcritureComptable);
	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnitViolation() throws Exception {
		manager.checkEcritureComptableUnit(vEcritureComptable);
	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnitRG2() throws Exception {
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new Date());
		vEcritureComptable.setLibelle("Libelle");
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(1234)));
		manager.checkEcritureComptableUnit(vEcritureComptable);
	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnitRG3() throws Exception {
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new Date());
		vEcritureComptable.setLibelle("Libelle");
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, null, null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, null, null));
		manager.checkEcritureComptableUnit(vEcritureComptable);

	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnitRG5() throws Exception {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		//String anneeReference = sdf.format(vEcritureComptable.getDate());
		Integer vCurrentYear =  LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()).toLocalDate().getYear();
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new Date());
		vEcritureComptable.setLibelle("Libelle");
		 vEcritureComptable.setReference("AC-" + (vCurrentYear - 1) + "/00001");
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));

		 Assert.assertEquals((vEcritureComptable.getReference().substring(3, 7)), ("2017"));
		 Assert.assertEquals((vEcritureComptable.getJournal().getCode().substring(0, 2)), ("AC"));
		 manager.checkEcritureComptableUnit(vEcritureComptable);
		
		 vEcritureComptable.setReference("DC-" + vCurrentYear + "/00001");
                 manager.checkEcritureComptableUnit(vEcritureComptable);
	


	}

	@Test(expected = NotFoundException.class)
	public void addReference() throws Exception {
		vEcritureComptable.setId(-1);
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		try {
			vEcritureComptable.setDate(new SimpleDateFormat("yyyy/MM/dd").parse("2016/12/31"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 vEcritureComptable.setLibelle("Cartouches d’imprimante");

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(606),
                "Cartouches d’imprimante", new BigDecimal(43),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(4456),
                "TVA 20%", new BigDecimal(8),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                "Facture F110001", null,
                new BigDecimal(51)));

		managerIntegration.addReference(vEcritureComptable);
		
		  vEcritureComptable.setDate(new Date());
            managerIntegration.addReference(vEcritureComptable);

	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptable() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();
		Date vCurrentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String vCurrentYear = sdf.format(vCurrentDate);
		// System.out.println(" year "+vCurrentYear);
		vEcritureComptable.setJournal(new JournalComptable("VE", "Vente"));
		vEcritureComptable.setDate(vCurrentDate);
		vEcritureComptable.setLibelle("Libelle");
		vEcritureComptable.setReference("VE-" + vCurrentYear + "/00004");
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
		manager.checkEcritureComptable(vEcritureComptable);
		manager.checkEcritureComptableContext(vEcritureComptable);
	}

	@Test
	public void checkEcritureComptableContext() throws Exception {
		vEcritureComptable.setReference("VE-2016/00001");
		manager.checkEcritureComptableContext(vEcritureComptable);
	}

	@Test
	public void checkEcritureComptableContextRG6() throws Exception {
		 vEcritureComptable.setReference("VE-2016/00002");
                  manager.checkEcritureComptableContext(vEcritureComptable);

		 vEcritureComptable.setId(0);
                 vEcritureComptable.setReference("VE-2016/00002");
                 manager.checkEcritureComptableContext(vEcritureComptable);
	}

}
