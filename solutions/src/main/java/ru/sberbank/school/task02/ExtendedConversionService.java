package ru.sberbank.school.task02;

import org.graalvm.compiler.lir.aarch64.AArch64AddressValue;
import ru.sberbank.school.task02.util.Beneficiary;
import ru.sberbank.school.task02.util.ClientOperation;
import ru.sberbank.school.task02.util.Quote;
import ru.sberbank.school.task02.util.Symbol;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ExtendedConversionService extends ConversionService implements ExtendedFxConversionService {

    public ExtendedConversionService(ExternalQuotesService externalQuotesService) {
        super(externalQuotesService);
    }

    @Override
    public Optional<BigDecimal> convertReversed(ClientOperation operation, Symbol symbol, BigDecimal amount, Beneficiary beneficiary) {
        return Optional.empty();
    }

    private Quote getQuote(ClientOperation operation, Symbol symbol,BigDecimal amount) {
        List<Quote> quotes = getExternalQuotesService().getQuotes(symbol);
        Quote prevQuote;
        BigDecimal priceQuote;
        for (Quote quoteEl: quotes) {
            priceQuote = getPrice(operation, quoteEl);
            if (amount.compareTo(quoteEl.getVolumeSize().divide(priceQuote)) < 0) {

            }
        }
        return null;
    }

    private Quote previousQouteAsc(Quote quote, BigDecimal amount, List<Quote> quotes) {
        Quote nearQuote = null;
        Quote infQuote = null;
        for (Quote quoteEl: quotes) {

        }
        return null;
    }


    @Override
    public Optional<BigDecimal> convertReversed(ClientOperation operation, Symbol symbol, BigDecimal amount, double delta, Beneficiary beneficiary) {
        return Optional.empty();
    }
}
