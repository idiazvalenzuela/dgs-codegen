package kotlin2.interfaceDocs.expected.client

import com.netflix.graphql.dgs.client.codegen.GraphQLProjection

public class TitledProjection : GraphQLProjection() {
  public val title: TitledProjection
    get() {
      field("title")
      return this
    }
}
