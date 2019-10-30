package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    Varasto kuormitettuVarasto;
    Varasto negatiivisenTilavuudenIlmoitettuVarasto;
    Varasto negatiivisenSaldonIlmoitettuKuormitettuVarasto;
    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
        negatiivisenTilavuudenIlmoitettuVarasto = new Varasto(-1);
        negatiivisenSaldonIlmoitettuKuormitettuVarasto = new Varasto(-1, -1);
        kuormitettuVarasto = new Varasto(10, 3);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void negatiivisenOttaminenEiMuutaSaldoa() {
        kuormitettuVarasto.otaVarastosta(-1);
        assertEquals(3, kuormitettuVarasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void liianOttaminenOttaaVainNiinPaljonKuinPystyy() {
        kuormitettuVarasto.otaVarastosta(11);
        assertEquals(0, kuormitettuVarasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void negatiivisenLisaaminenEiMuutaSaldoa() {
        kuormitettuVarasto.lisaaVarastoon(-1);
        assertEquals(3, kuormitettuVarasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void liianLisaaminenTayttaaVainNiinPaljonKuinPystyy() {
        varasto.lisaaVarastoon(11);
        assertEquals(10, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void negatiivinenTilavuusNollataan() {
        assertEquals(0, negatiivisenTilavuudenIlmoitettuVarasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void negatiivinenTilavuusNollataanKunAlkuSaldoIlmoitettu() {
        Varasto varasto2 = new Varasto(-1, 1);
        assertEquals(0, varasto2.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void negatiivinenSaldoNollataan() {
        assertEquals(0, negatiivisenSaldonIlmoitettuKuormitettuVarasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void toStringToimii() {
        assertEquals("saldo = 3.0, vielä tilaa 7.0", kuormitettuVarasto.toString());
    }
}
