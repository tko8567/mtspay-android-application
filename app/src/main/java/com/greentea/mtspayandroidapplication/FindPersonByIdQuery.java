package com.greentea.mtspayandroidapplication;

import com.apollographql.apollo.api.*;
import com.apollographql.apollo.api.internal.UnmodifiableMapBuilder;
import com.apollographql.apollo.api.internal.Utils;
import type.CustomType;
import type.TransactionStatus;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;

@Generated("Apollo GraphQL")
public final class FindPersonByIdQuery implements Query<FindPersonByIdQuery.Data, FindPersonByIdQuery.Data, FindPersonByIdQuery.Variables> {
  public static final String OPERATION_DEFINITION = "query FindPersonById($id: String) {\n"
      + "  person(id: $id) {\n"
      + "    __typename\n"
      + "    id\n"
      + "    firstName\n"
      + "    lastName\n"
      + "    cards {\n"
      + "      __typename\n"
      + "      id\n"
      + "      number\n"
      + "      validThru\n"
      + "      balance\n"
      + "      isDefault\n"
      + "    }\n"
      + "    transactions {\n"
      + "      __typename\n"
      + "      id\n"
      + "      from {\n"
      + "        __typename\n"
      + "        firstName\n"
      + "        lastName\n"
      + "      }\n"
      + "      to {\n"
      + "        __typename\n"
      + "        ... on Person {\n"
      + "          firstName\n"
      + "          lastName\n"
      + "        }\n"
      + "        ... on Organization {\n"
      + "          name\n"
      + "        }\n"
      + "      }\n"
      + "      amount\n"
      + "      description\n"
      + "      status\n"
      + "    }\n"
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
    return "0ef6dfd896fa9a35d228567ec35cac1b1d8f6db6f7958d488ae04f6d3e7ca0e2";
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
      ResponseField.forString("id", "id", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("firstName", "firstName", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("lastName", "lastName", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("cards", "cards", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("transactions", "transactions", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String id;

    final @Nullable String firstName;

    final @Nullable String lastName;

    final @Nullable List<Card> cards;

    final @Nullable List<Transaction> transactions;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Person(@Nonnull String __typename, @Nullable String id, @Nullable String firstName,
        @Nullable String lastName, @Nullable List<Card> cards,
        @Nullable List<Transaction> transactions) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.cards = cards;
      this.transactions = transactions;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String id() {
      return this.id;
    }

    public @Nullable String firstName() {
      return this.firstName;
    }

    public @Nullable String lastName() {
      return this.lastName;
    }

    public @Nullable List<Card> cards() {
      return this.cards;
    }

    public @Nullable List<Transaction> transactions() {
      return this.transactions;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], id);
          writer.writeString($responseFields[2], firstName);
          writer.writeString($responseFields[3], lastName);
          writer.writeList($responseFields[4], cards, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Card) value).marshaller());
            }
          });
          writer.writeList($responseFields[5], transactions, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Transaction) value).marshaller());
            }
          });
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Person{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "firstName=" + firstName + ", "
          + "lastName=" + lastName + ", "
          + "cards=" + cards + ", "
          + "transactions=" + transactions
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
         && ((this.id == null) ? (that.id == null) : this.id.equals(that.id))
         && ((this.firstName == null) ? (that.firstName == null) : this.firstName.equals(that.firstName))
         && ((this.lastName == null) ? (that.lastName == null) : this.lastName.equals(that.lastName))
         && ((this.cards == null) ? (that.cards == null) : this.cards.equals(that.cards))
         && ((this.transactions == null) ? (that.transactions == null) : this.transactions.equals(that.transactions));
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
        h ^= (id == null) ? 0 : id.hashCode();
        h *= 1000003;
        h ^= (firstName == null) ? 0 : firstName.hashCode();
        h *= 1000003;
        h ^= (lastName == null) ? 0 : lastName.hashCode();
        h *= 1000003;
        h ^= (cards == null) ? 0 : cards.hashCode();
        h *= 1000003;
        h ^= (transactions == null) ? 0 : transactions.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<Person> {
      final Card.Mapper cardFieldMapper = new Card.Mapper();

      final Transaction.Mapper transactionFieldMapper = new Transaction.Mapper();

      @Override
      public Person map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readString($responseFields[1]);
        final String firstName = reader.readString($responseFields[2]);
        final String lastName = reader.readString($responseFields[3]);
        final List<Card> cards = reader.readList($responseFields[4], new ResponseReader.ListReader<Card>() {
          @Override
          public Card read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<Card>() {
              @Override
              public Card read(ResponseReader reader) {
                return cardFieldMapper.map(reader);
              }
            });
          }
        });
        final List<Transaction> transactions = reader.readList($responseFields[5], new ResponseReader.ListReader<Transaction>() {
          @Override
          public Transaction read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<Transaction>() {
              @Override
              public Transaction read(ResponseReader reader) {
                return transactionFieldMapper.map(reader);
              }
            });
          }
        });
        return new Person(__typename, id, firstName, lastName, cards, transactions);
      }
    }
  }

  public static class Card {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("id", "id", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("number", "number", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("validThru", "validThru", null, true, CustomType.DATE, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("balance", "balance", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forBoolean("isDefault", "isDefault", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String id;

    final @Nullable String number;

    final @Nullable Object validThru;

    final @Nullable Double balance;

    final @Nullable Boolean isDefault;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Card(@Nonnull String __typename, @Nullable String id, @Nullable String number,
        @Nullable Object validThru, @Nullable Double balance, @Nullable Boolean isDefault) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = id;
      this.number = number;
      this.validThru = validThru;
      this.balance = balance;
      this.isDefault = isDefault;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String id() {
      return this.id;
    }

    public @Nullable String number() {
      return this.number;
    }

    public @Nullable Object validThru() {
      return this.validThru;
    }

    public @Nullable Double balance() {
      return this.balance;
    }

    public @Nullable Boolean isDefault() {
      return this.isDefault;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], id);
          writer.writeString($responseFields[2], number);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[3], validThru);
          writer.writeDouble($responseFields[4], balance);
          writer.writeBoolean($responseFields[5], isDefault);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Card{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "number=" + number + ", "
          + "validThru=" + validThru + ", "
          + "balance=" + balance + ", "
          + "isDefault=" + isDefault
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Card) {
        Card that = (Card) o;
        return this.__typename.equals(that.__typename)
         && ((this.id == null) ? (that.id == null) : this.id.equals(that.id))
         && ((this.number == null) ? (that.number == null) : this.number.equals(that.number))
         && ((this.validThru == null) ? (that.validThru == null) : this.validThru.equals(that.validThru))
         && ((this.balance == null) ? (that.balance == null) : this.balance.equals(that.balance))
         && ((this.isDefault == null) ? (that.isDefault == null) : this.isDefault.equals(that.isDefault));
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
        h ^= (id == null) ? 0 : id.hashCode();
        h *= 1000003;
        h ^= (number == null) ? 0 : number.hashCode();
        h *= 1000003;
        h ^= (validThru == null) ? 0 : validThru.hashCode();
        h *= 1000003;
        h ^= (balance == null) ? 0 : balance.hashCode();
        h *= 1000003;
        h ^= (isDefault == null) ? 0 : isDefault.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<Card> {
      @Override
      public Card map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readString($responseFields[1]);
        final String number = reader.readString($responseFields[2]);
        final Object validThru = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[3]);
        final Double balance = reader.readDouble($responseFields[4]);
        final Boolean isDefault = reader.readBoolean($responseFields[5]);
        return new Card(__typename, id, number, validThru, balance, isDefault);
      }
    }
  }

  public static class Transaction {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("id", "id", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("from", "from", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("to", "to", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("amount", "amount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("description", "description", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String id;

    final @Nullable From from;

    final @Nullable To to;

    final @Nullable Double amount;

    final @Nullable String description;

    final @Nullable TransactionStatus status;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Transaction(@Nonnull String __typename, @Nullable String id, @Nullable From from,
        @Nullable To to, @Nullable Double amount, @Nullable String description,
        @Nullable TransactionStatus status) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = id;
      this.from = from;
      this.to = to;
      this.amount = amount;
      this.description = description;
      this.status = status;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String id() {
      return this.id;
    }

    public @Nullable From from() {
      return this.from;
    }

    public @Nullable To to() {
      return this.to;
    }

    public @Nullable Double amount() {
      return this.amount;
    }

    public @Nullable String description() {
      return this.description;
    }

    public @Nullable TransactionStatus status() {
      return this.status;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], id);
          writer.writeObject($responseFields[2], from != null ? from.marshaller() : null);
          writer.writeObject($responseFields[3], to != null ? to.marshaller() : null);
          writer.writeDouble($responseFields[4], amount);
          writer.writeString($responseFields[5], description);
          writer.writeString($responseFields[6], status != null ? status.name() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Transaction{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "from=" + from + ", "
          + "to=" + to + ", "
          + "amount=" + amount + ", "
          + "description=" + description + ", "
          + "status=" + status
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Transaction) {
        Transaction that = (Transaction) o;
        return this.__typename.equals(that.__typename)
         && ((this.id == null) ? (that.id == null) : this.id.equals(that.id))
         && ((this.from == null) ? (that.from == null) : this.from.equals(that.from))
         && ((this.to == null) ? (that.to == null) : this.to.equals(that.to))
         && ((this.amount == null) ? (that.amount == null) : this.amount.equals(that.amount))
         && ((this.description == null) ? (that.description == null) : this.description.equals(that.description))
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status));
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
        h ^= (id == null) ? 0 : id.hashCode();
        h *= 1000003;
        h ^= (from == null) ? 0 : from.hashCode();
        h *= 1000003;
        h ^= (to == null) ? 0 : to.hashCode();
        h *= 1000003;
        h ^= (amount == null) ? 0 : amount.hashCode();
        h *= 1000003;
        h ^= (description == null) ? 0 : description.hashCode();
        h *= 1000003;
        h ^= (status == null) ? 0 : status.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<Transaction> {
      final From.Mapper fromFieldMapper = new From.Mapper();

      final To.Mapper toFieldMapper = new To.Mapper();

      @Override
      public Transaction map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readString($responseFields[1]);
        final From from = reader.readObject($responseFields[2], new ResponseReader.ObjectReader<From>() {
          @Override
          public From read(ResponseReader reader) {
            return fromFieldMapper.map(reader);
          }
        });
        final To to = reader.readObject($responseFields[3], new ResponseReader.ObjectReader<To>() {
          @Override
          public To read(ResponseReader reader) {
            return toFieldMapper.map(reader);
          }
        });
        final Double amount = reader.readDouble($responseFields[4]);
        final String description = reader.readString($responseFields[5]);
        final String statusStr = reader.readString($responseFields[6]);
        final TransactionStatus status;
        if (statusStr != null) {
          status = TransactionStatus.valueOf(statusStr);
        } else {
          status = null;
        }
        return new Transaction(__typename, id, from, to, amount, description, status);
      }
    }
  }

  public static class From {
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

    public From(@Nonnull String __typename, @Nullable String firstName, @Nullable String lastName) {
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
        $toString = "From{"
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
      if (o instanceof From) {
        From that = (From) o;
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

    public static final class Mapper implements ResponseFieldMapper<From> {
      @Override
      public From map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String firstName = reader.readString($responseFields[1]);
        final String lastName = reader.readString($responseFields[2]);
        return new From(__typename, firstName, lastName);
      }
    }
  }

  public static class To {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInlineFragment("__typename", "__typename", Arrays.asList("Person")),
      ResponseField.forInlineFragment("__typename", "__typename", Arrays.asList("Organization"))
    };

    final @Nonnull String __typename;

    final @Nullable AsPerson asPerson;

    final @Nullable AsOrganization asOrganization;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public To(@Nonnull String __typename, @Nullable AsPerson asPerson,
        @Nullable AsOrganization asOrganization) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.asPerson = asPerson;
      this.asOrganization = asOrganization;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable AsPerson asPerson() {
      return this.asPerson;
    }

    public @Nullable AsOrganization asOrganization() {
      return this.asOrganization;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          final AsPerson $asPerson = asPerson;
          if ($asPerson != null) {
            $asPerson.marshaller().marshal(writer);
          }
          final AsOrganization $asOrganization = asOrganization;
          if ($asOrganization != null) {
            $asOrganization.marshaller().marshal(writer);
          }
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "To{"
          + "__typename=" + __typename + ", "
          + "asPerson=" + asPerson + ", "
          + "asOrganization=" + asOrganization
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof To) {
        To that = (To) o;
        return this.__typename.equals(that.__typename)
         && ((this.asPerson == null) ? (that.asPerson == null) : this.asPerson.equals(that.asPerson))
         && ((this.asOrganization == null) ? (that.asOrganization == null) : this.asOrganization.equals(that.asOrganization));
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
        h ^= (asPerson == null) ? 0 : asPerson.hashCode();
        h *= 1000003;
        h ^= (asOrganization == null) ? 0 : asOrganization.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<To> {
      final AsPerson.Mapper asPersonFieldMapper = new AsPerson.Mapper();

      final AsOrganization.Mapper asOrganizationFieldMapper = new AsOrganization.Mapper();

      @Override
      public To map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final AsPerson asPerson = reader.readConditional($responseFields[1], new ResponseReader.ConditionalTypeReader<AsPerson>() {
          @Override
          public AsPerson read(String conditionalType, ResponseReader reader) {
            return asPersonFieldMapper.map(reader);
          }
        });
        final AsOrganization asOrganization = reader.readConditional($responseFields[2], new ResponseReader.ConditionalTypeReader<AsOrganization>() {
          @Override
          public AsOrganization read(String conditionalType, ResponseReader reader) {
            return asOrganizationFieldMapper.map(reader);
          }
        });
        return new To(__typename, asPerson, asOrganization);
      }
    }
  }

  public static class AsPerson {
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

    public AsPerson(@Nonnull String __typename, @Nullable String firstName,
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
        $toString = "AsPerson{"
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
      if (o instanceof AsPerson) {
        AsPerson that = (AsPerson) o;
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

    public static final class Mapper implements ResponseFieldMapper<AsPerson> {
      @Override
      public AsPerson map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String firstName = reader.readString($responseFields[1]);
        final String lastName = reader.readString($responseFields[2]);
        return new AsPerson(__typename, firstName, lastName);
      }
    }
  }

  public static class AsOrganization {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String name;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public AsOrganization(@Nonnull String __typename, @Nullable String name) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.name = name;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String name() {
      return this.name;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], name);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "AsOrganization{"
          + "__typename=" + __typename + ", "
          + "name=" + name
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof AsOrganization) {
        AsOrganization that = (AsOrganization) o;
        return this.__typename.equals(that.__typename)
         && ((this.name == null) ? (that.name == null) : this.name.equals(that.name));
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
        h ^= (name == null) ? 0 : name.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<AsOrganization> {
      @Override
      public AsOrganization map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String name = reader.readString($responseFields[1]);
        return new AsOrganization(__typename, name);
      }
    }
  }
}
