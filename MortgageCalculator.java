package mortgage;

import mortgage.model.InputData;
import mortgage.model.MortgageType;
import mortgage.model.Overpayment;
import mortgage.services.AmountsCalculationServiceImpl;
import mortgage.services.ConstantAmountsCalculationServiceImpl;
import mortgage.services.DecreasingAmountsCalculationServiceImpl;
import mortgage.services.MortgageCalculationService;
import mortgage.services.MortgageCalculationServiceImpl;
import mortgage.services.OverpaymentCalculationServiceImpl;
import mortgage.services.PrintingService;
import mortgage.services.PrintingServiceImpl;
import mortgage.services.RateCalculationService;
import mortgage.services.RateCalculationServiceImpl;
import mortgage.services.ReferenceCalculationServiceImpl;
import mortgage.services.ResidualCalculationServiceImpl;
import mortgage.services.SummaryServiceFactory;
import mortgage.services.TimePointCalculationServiceImpl;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class MortgageCalculator {

    public static void main(String[] args) {

        // To jest rodzaj mapy, który w tym przypadku trzyma klucze posortowane rosnąco.
        // Wiem, że nie tłumaczyłem go wcześniej. Spokojnie, jeszcze będzie ;)
        Map<Integer, BigDecimal> overpaymentSchema = new TreeMap<>();
        overpaymentSchema.put(5, BigDecimal.valueOf(12000));
        overpaymentSchema.put(19, BigDecimal.valueOf(10000));
        overpaymentSchema.put(28, BigDecimal.valueOf(11000));
        overpaymentSchema.put(64, BigDecimal.valueOf(16000));
        overpaymentSchema.put(78, BigDecimal.valueOf(18000));

        // Ważne jest to aby zwrócić uwagę, na to, które dane wejściowe są nadpisywane przez withery
        InputData defaultInputData = new InputData()
            .withAmount(new BigDecimal("296192.11"))
            .withMonthsDuration(BigDecimal.valueOf(360))
            .withOverpaymentReduceWay(Overpayment.REDUCE_PERIOD)
            .withType(MortgageType.DECREASING)
            .withOverpaymentSchema(overpaymentSchema);

        PrintingService printingService = new PrintingServiceImpl();
        RateCalculationService rateCalculationService = new RateCalculationServiceImpl(
            new TimePointCalculationServiceImpl(),
            new OverpaymentCalculationServiceImpl(),
            new AmountsCalculationServiceImpl(
                new ConstantAmountsCalculationServiceImpl(),
                new DecreasingAmountsCalculationServiceImpl()
            ),
            new ResidualCalculationServiceImpl(),
            new ReferenceCalculationServiceImpl()
        );

        MortgageCalculationService mortgageCalculationService = new MortgageCalculationServiceImpl(
            rateCalculationService,
            printingService,
            SummaryServiceFactory.create()
        );

        mortgageCalculationService.calculate(defaultInputData);
    }
}
