package mortgage.model;

import java.math.BigDecimal;

public class MortgageResidual {

    private final BigDecimal residualAmount;

    private final BigDecimal residualDuration;

    public MortgageResidual(final BigDecimal residualAmount, final BigDecimal residualDuration) {
        this.residualAmount = residualAmount;
        this.residualDuration = residualDuration;
    }

    public BigDecimal getResidualAmount() {
        return residualAmount;
    }

    public BigDecimal getResidualDuration() {
        return residualDuration;
    }
}
