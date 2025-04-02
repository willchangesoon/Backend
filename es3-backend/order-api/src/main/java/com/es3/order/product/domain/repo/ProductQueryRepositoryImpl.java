package com.es3.order.product.domain.repo;

import com.es3.order.product.domain.Product;
import com.es3.order.product.domain.QProduct;
import com.es3.order.product.dto.ProductSearchCond;
import com.es3.order.product.dto.SortDirection;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> searchProducts(ProductSearchCond cond, Pageable pageable) {
        QProduct product = QProduct.product;

        BooleanBuilder builder = new BooleanBuilder();

        if (cond.getCategoryId() != null) {
            builder.and(product.category.eq(cond.getCategoryId()));
        }

        if (cond.getStoreId() != null) {
            builder.and(product.store.id.eq(cond.getStoreId()));
        }

        if (Boolean.TRUE.equals(cond.getDiscounted())) {
            builder.and(product.discount.gt(0));
        }

        if (cond.getCursorId() != null) {
            builder.and(product.id.lt(cond.getCursorId()));
        }

        return queryFactory.selectFrom(product)
                .where(builder)
                .orderBy(cond.getSortDirection() == SortDirection.ASC
                        ? product.createdDate.asc()
                        : product.createdDate.desc())
                .limit(pageable.getPageSize())
                .fetch();
    }
}

