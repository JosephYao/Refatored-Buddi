package net.sourceforge.buddi.impl_2_2.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import net.sourceforge.buddi.api.model.ImmutableCategory;
import net.sourceforge.buddi.api.model.ImmutableSource;

import org.eclipse.emf.common.util.EList;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Source;

public class ImmutableCategoryImpl extends ImmutableSourceImpl implements ImmutableCategory {

    private Category category;
    
    public Category getImpl()
    {
        return category;
    }
    
    public ImmutableCategoryImpl(Category category)
    {
        super(category);

        this.category = category;
    }

    public long getBudgetedAmount() {
        return category.getBudgetedAmount();
    }

    public boolean isIncome() {
        return category.isIncome();
    }

    private ImmutableCategory immutableCategoryParent = null;
    public ImmutableCategory getParent() {
        if (null == immutableCategoryParent)
        {
            immutableCategoryParent = new ImmutableCategoryImpl(category.getParent());
        }
        return immutableCategoryParent;
    }

    protected void removeChild(ImmutableCategory child) {
        if (null == immutableCategoryChildrenList)
        {
            immutableCategoryChildrenList.remove(child);
        }
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
    
    public int compareTo(ImmutableCategory immutableCategory) {
        return category.compareTo(((ImmutableCategoryImpl)immutableCategory).getImpl());
    }

    public boolean equals(Object obj) {
        if (false == obj instanceof ImmutableCategoryImpl)
        {
            return false;
        }

        ImmutableCategoryImpl immutableCategoryImpl = (ImmutableCategoryImpl)obj;
        
        return 0 == compareTo(immutableCategoryImpl);
    }

}
