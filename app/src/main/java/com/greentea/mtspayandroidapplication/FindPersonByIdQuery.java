package com.greentea.mtspayandroidapplication;

import com.apollographql.apollo.api.*;
import com.apollographql.apollo.api.internal.UnmodifiableMapBuilder;
import com.apollographql.apollo.api.internal.Utils;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Generated("Apollo GraphQL")
public final class FindPersonByIdQuery implements Query<FindPersonByIdQuery.Data, FindPersonByIdQuery.Data, FindPersonByIdQuery.Variables> {
  public static final String OPERATION_DEFINITION = "query FindPersonById($id: String) {\n"
      + "  person(id: $id) {\n"
      + "    __typename\n"
      + "    firstName\n"
      + "    lastName\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "FindPersonById";
    }
  };

  private final FindPersonByIdQuery.Variables variables;

  public FindPersonByIdQuery(@Nullable String id) {
    variables = new FindPersonByIdQuery.Variables(id);
  }

  @Override
  public String operationId() {
    return "bd8f22470590cd075611552e8303df0735d1aaf43095cdc74618cf0a942991da";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public FindPersonByIdQuery.Data wrapData(FindPersonByIdQuery.Data data) {
    return data;
  }

  @Override
  public FindPersonByIdQuery.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<Data> responseFieldMapper() {
    return new Data.Mapper();
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public OperationName name() {
    return OPERATION_NAME;
  }

  public static final class Builder {
    private @Nullable String id;

    Builder() {
    }

    public Builder id(@Nullable String id) {
      this.id = id;
      return this;
    }

    public FindPersonByIdQuery build() {
      return new FindPersonByIdQuery(id);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nullable String id;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nullable String id) {
      this.id = id;
      this.valueMap.put("id", id);
    }

    public @Nullable String id() {
      return id;
    }

    @Override
    public Map<String, Object> valueMap() {
      return Collections.unmodifiableMap(valueMap);
    }

    @Override
    public InputFieldMarshaller marshaller() {
      return new InputFieldMarshaller() {
        @Override
        public void marshal(InputFieldWriter writer) throws IOException {
          writer.writeString("id", id);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("person", "person", new UnmodifiableMapBuilder<String, Object>(1)
        .put("id", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "id")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable Person person;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable Person person) {
      this.person = person;
    }

    public @Nullable Person person() {
      return this.person;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], person != null ? person.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "person=" + person
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Data) {
        Data that = (Data) o;
        return ((this.person == null) ? (that.person == null) : this.person.equals(that.person));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (person == null) ? 0 : person.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final Person.Mapper personFieldMapper = new Person.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final Person person = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<Person>() {
          @Override
          public Person read(ResponseReader reader) {
            return personFieldMapper.map(reader);
          }
        });
        return new Data(person);
      }
    }
  }

  public static class Person {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("firstName", "firstName", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("lastName", "lastName", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String firstName;

    final @Nullable String lastName;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Person(@Nonnull String __typename, @Nullable String firstName,
        @Nullable String lastName) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.firstName = firstName;
      this.lastName = lastName;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String firstName() {
      return this.firstName;
    }

    public @Nullable String lastName() {
      return this.lastName;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], firstName);
          writer.writeString($responseFields[2], lastName);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Person{"
          + "__typename=" + __typename + ", "
          + "firstName=" + firstName + ", "
          + "lastName=" + lastName
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Person) {
        Person that = (Person) o;
        return this.__typename.equals(that.__typename)
         && ((this.firstName == null) ? (that.firstName == null) : this.firstName.equals(that.firstName))
         && ((this.lastName == null) ? (that.lastName == null) : this.lastName.equals(that.lastName));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= __typename.hashCode();
        h *= 1000003;
        h ^= (firstName == null) ? 0 : firstName.hashCode();
        h *= 1000003;
        h ^= (lastName == null) ? 0 : lastName.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<Person> {
      @Override
      public Person map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String firstName = reader.readString($responseFields[1]);
        final String lastName = reader.readString($responseFields[2]);
        return new Person(__typename, firstName, lastName);
      }
    }
  }
}
