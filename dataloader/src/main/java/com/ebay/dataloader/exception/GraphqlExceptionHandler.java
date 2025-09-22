package com.ebay.dataloader.exception;

import graphql.GraphQLError;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GraphqlExceptionHandler {


  @GraphQlExceptionHandler //handleProductNotFoundException(RuntimeException.class)
  public GraphQLError handle(ProductNotFoundException ex) {
    return GraphQLError.newError().message(ex.getMessage()).build();
  }

}
