package ru.sberbank.school.task02;

import ru.sberbank.school.task02.util.ClientOperation;
import ru.sberbank.school.task02.util.Symbol;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {

        ExternalQuotesService myExternalQuotesService = new MyExternalQuotesService();
        ServiceFactoryImpl serviceFactoryImpl = new ServiceFactoryImpl();
        FxConversionService conversionService = serviceFactoryImpl.getFxConversionService(myExternalQuotesService);

   /*     System.out.println(conversionService.convert(ClientOperation.BUY,
                Symbol.USD_RUB,
                BigDecimal.valueOf(0)));
        System.out.println(conversionService.convert(ClientOperation.BUY,
                Symbol.USD_RUB,
                BigDecimal.valueOf(80)));
        System.out.println(conversionService.convert(ClientOperation.BUY,
                Symbol.USD_RUB,
                BigDecimal.valueOf(100)));
        System.out.println(conversionService.convert(ClientOperation.BUY,
                Symbol.USD_RUB,
                BigDecimal.valueOf(0.99999)));
        System.out.println(conversionService.convert(ClientOperation.BUY,
                Symbol.USD_RUB,
                BigDecimal.valueOf(1)));
        System.out.println(conversionService.convert(ClientOperation.BUY,
                Symbol.USD_RUB,
                BigDecimal.valueOf(1_000_000_000)));*/

        BigDecimal bigDecimal = new BigDecimal(25000);
        BigDecimal bigDecimal1 = new BigDecimal(53);
        System.out.println(bigDecimal.divide(bigDecimal));
        System.out.println(bigDecimal.divide(bigDecimal1));

    }
}
