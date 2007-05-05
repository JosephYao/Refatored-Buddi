package net.sourceforge.buddi.impl_2_4.manager;

import net.sourceforge.buddi.api.manager.UtilityManager;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.util.FormatterWrapper;

public class UtilityManagerImpl implements UtilityManager {

    public String translate(String key) {
        return Translate.getInstance().get(key);
    }

    public String getFormattedCurrency(long value, boolean negate) {
        return FormatterWrapper.getFormattedCurrency(value, negate);
    }

    public String getFormattedCurrency(long value) {
        return FormatterWrapper.getFormattedCurrency(value);
    }
}
