package net.sourceforge.buddi.impl_2_2.manager;

import net.sourceforge.buddi.api.manager.UtilityManager;
import net.sourceforge.buddi.api.translate.TranslateKeys;
import net.sourceforge.buddi.impl_2_2.exception.UnimplementedException;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.util.FormatterWrapper;

public class UtilityManagerImpl implements UtilityManager {

    public String translate(String key) {
        return Translate.getInstance().get(key);
    }

    public String translate(TranslateKeys key) {
        org.homeunix.drummer.controller.TranslateKeys implKey = null;
        switch (key)
        {
        // Limited support due to manual work involved.
        
        case CHOOSE_EXPORT_FILE:
            implKey = org.homeunix.drummer.controller.TranslateKeys.CHOOSE_EXPORT_FILE;
        case ERROR:
            implKey = org.homeunix.drummer.controller.TranslateKeys.ERROR;
        case EXPORT_TO_CSV:
            implKey = org.homeunix.drummer.controller.TranslateKeys.EXPORT_TO_CSV;
        case FILE_SAVED:
            implKey = org.homeunix.drummer.controller.TranslateKeys.FILE_SAVED;
        case SUCCESSFUL_EXPORT:
            implKey = org.homeunix.drummer.controller.TranslateKeys.SUCCESSFUL_EXPORT;
        }
        
        if (null == implKey)
        {
            throw new UnimplementedException("Unsupported key translation for " + key);
        }
        
        return Translate.getInstance().get(implKey);
    }

    public String getFormattedCurrency(long value, boolean negate) {
        return FormatterWrapper.getFormattedCurrency(value, negate);
    }

    public String getFormattedCurrency(long value) {
        return FormatterWrapper.getFormattedCurrency(value);
    }
}
