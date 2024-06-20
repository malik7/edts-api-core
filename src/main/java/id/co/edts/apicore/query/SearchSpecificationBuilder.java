package id.co.edts.apicore.query;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SearchSpecificationBuilder<T> {

    private final List<SearchCriteria> params;
    private final List<Specification<T>> specificationAnd;
    private final List<Specification<T>> specificationOr;
    private final boolean isOrPredicate;

    public SearchSpecificationBuilder(boolean isOrPredicate) {
        this.isOrPredicate = isOrPredicate;
        this.params = new ArrayList<>();
        this.specificationAnd = new ArrayList<>();
        this.specificationOr = new ArrayList<>();
    }

    public SearchSpecificationBuilder<T> with(SearchCriteria criteria) {
        this.params.add(criteria);
        return this;
    }

    public boolean isEmpty() {
        return this.params.isEmpty();
    }

    public SearchSpecificationBuilder<T> and(SearchSpecificationBuilder<T> specification) {
        specificationAnd.add(specification.getSpecification());
        return this;
    }

    public SearchSpecificationBuilder<T> or(SearchSpecificationBuilder<T> specification) {
        specificationOr.add(specification.getSpecification());
        return this;
    }

    public Specification<T> build() {
        var spec = getSpecification();

        if(!specificationAnd.isEmpty()){
            for(Specification<T> specJoin : specificationAnd){
                spec = spec.and(specJoin);
            }
        }

        if(!specificationOr.isEmpty()){
            for(Specification<T> specJoin : specificationOr){
                spec = spec.and(specJoin);
            }
        }

        return spec;
    }

    private Specification<T> getSpecification() {
        List<Specification<T>> specs = new ArrayList<>();

        if (this.params.size() == 0)
            return Specification.where(null);

        for (SearchCriteria criteria : this.params) {
            specs.add(new SearchSpecification<>(criteria));
        }


        Specification<T> result = specs.get(0);
        if (specs.size() == 1)
            return result;

        Specification<T> specification = Specification.where(result);

        for (int i = 1; i < this.params.size(); i++)
            specification = this.isOrPredicate ? specification.or(specs.get(i)) : specification.and(specs.get(i));
        return specification;
    }

}
