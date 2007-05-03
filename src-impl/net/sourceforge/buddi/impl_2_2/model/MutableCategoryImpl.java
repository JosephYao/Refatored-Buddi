package net.sourceforge.buddi.impl_2_2.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import net.sourceforge.buddi.api.exception.ValidationException;
import net.sourceforge.buddi.api.model.ImmutableCategory;
import net.sourceforge.buddi.api.model.ImmutableSource;
import net.sourceforge.buddi.api.model.MutableCategory;

import org.eclipse.emf.common.util.EList;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Source;

public class MutableCategoryImpl extends MutableSourceImpl implements MutableCategory, ImmutableCategory {

    private Category category;
    
    public Category getImpl() {
        return category;
    }

    public MutableCategoryImpl(Category category) {
        super(category);
        
        this.category = category;
    }

    public long getBudgetedAmount() {
        return category.getBudgetedAmount();
    }

    public void setBudgetedAmount(long budgetedAmount) {
        category.setBudgetedAmount(budgetedAmount);
    }

    public boolean isIncome() {
        return category.isIncome();
    }

    public void setIncome(boolean income) {
        category.setIncome(income);
    }

    public void setParent(ImmutableCategory parent) {
        if (null == immutableCategoryParent)
        {
            removeChildFromParent(this, parent);
        }
        
        immutableCategoryParent = parent;
        
        if (null == immutableCategoryParent)
        {
            removeChildFromParent(this, parent);
        }
    }

    private void removeChildFromParent(ImmutableCategory child, ImmutableCategory parent) {
        if (parent instanceof MutableCategoryImpl)
        {
            ((MutableCategoryImpl)parent).removeChild(child);
        }
        else if (parent instanceof ImmutableCategoryImpl)
        {
            ((ImmutableCategoryImpl)parent).removeChild(child);
        }
    }

    protected void removeChild(ImmutableCategory child) {
        if (null == immutableCategoryChildrenList)
        {
            immutableCategoryChildrenList.remove(child);
        }
    }

    private ImmutableCategory immutableCategoryParent = null;
    public ImmutableCategory getParent() {
        if (null == immutableCategoryParent)
        {
            if (null == category.getParent())
            {
                immutableCategoryParent = null;
            }
            else
            {
                immutableCategoryParent = new ImmutableCategoryImpl(category.getParent());
            }
        }
        return immutableCategoryParent;
    }

    private Collection<ImmutableSource> immutableCategoryChildrenList = null;
    public Collection<ImmutableSource> getChildren() {
        
        EList categoryEList = category.getChildren();
        if (null == categoryEList)
        {
            return Collections.emptyList();
        }

        if (null == immutableCategoryChildrenList)
        {
            immutableCategoryChildrenList = new ArrayList<ImmutableSource>();
            
            for (Object o: categoryEList) {
                if (o instanceof Category){
                    Category child = (Category) o;
                    ImmutableCategoryImpl immutableCategoryChild = new ImmutableCategoryImpl(child);
                    immutableCategoryChildrenList.add(immutableCategoryChild);
                }
                else if (o instanceof Account){
                    Account child = (Account) o;
                    ImmutableAccountImpl immutableAccountChild = new ImmutableAccountImpl(child);
                    immutableCategoryChildrenList.add(immutableAccountChild);
                }
                else {
                    Source child = (Source) o;
                    ImmutableSourceImpl immutableSourceChild = new ImmutableSourceImpl(child);
                    immutableCategoryChildrenList.add(immutableSourceChild);
                }
            }
        }
        
        return immutableCategoryChildrenList;
    }

    public void validate() throws ValidationException {
        super.validate();
    }

    
    public boolean equals(Object obj) {
        if (false == obj instanceof MutableCategoryImpl)
        {
            return false;
        }

        MutableCategoryImpl mutableCategoryImpl = (MutableCategoryImpl)obj;
        
        return 0 == compareTo(mutableCategoryImpl);
    }
}
