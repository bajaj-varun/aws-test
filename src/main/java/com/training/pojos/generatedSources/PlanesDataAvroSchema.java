/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.training.pojos.generatedSources;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class PlanesDataAvroSchema extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 5652806325378950133L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"PlanesDataAvroSchema\",\"namespace\":\"com.training.pojos.generatedSources\",\"fields\":[{\"name\":\"tailnum\",\"type\":\"string\"},{\"name\":\"type\",\"type\":\"string\"},{\"name\":\"manufacturer\",\"type\":\"string\"},{\"name\":\"issue_date\",\"type\":\"string\"},{\"name\":\"model\",\"type\":\"string\"},{\"name\":\"status\",\"type\":\"string\"},{\"name\":\"aircraft_type\",\"type\":\"string\"},{\"name\":\"engine_type\",\"type\":\"string\"},{\"name\":\"year\",\"type\":\"int\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<PlanesDataAvroSchema> ENCODER =
      new BinaryMessageEncoder<PlanesDataAvroSchema>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<PlanesDataAvroSchema> DECODER =
      new BinaryMessageDecoder<PlanesDataAvroSchema>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<PlanesDataAvroSchema> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<PlanesDataAvroSchema> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<PlanesDataAvroSchema>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this PlanesDataAvroSchema to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a PlanesDataAvroSchema from a ByteBuffer. */
  public static PlanesDataAvroSchema fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.CharSequence tailnum;
  @Deprecated public java.lang.CharSequence type;
  @Deprecated public java.lang.CharSequence manufacturer;
  @Deprecated public java.lang.CharSequence issue_date;
  @Deprecated public java.lang.CharSequence model;
  @Deprecated public java.lang.CharSequence status;
  @Deprecated public java.lang.CharSequence aircraft_type;
  @Deprecated public java.lang.CharSequence engine_type;
  @Deprecated public int year;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public PlanesDataAvroSchema() {}

  /**
   * All-args constructor.
   * @param tailnum The new value for tailnum
   * @param type The new value for type
   * @param manufacturer The new value for manufacturer
   * @param issue_date The new value for issue_date
   * @param model The new value for model
   * @param status The new value for status
   * @param aircraft_type The new value for aircraft_type
   * @param engine_type The new value for engine_type
   * @param year The new value for year
   */
  public PlanesDataAvroSchema(java.lang.CharSequence tailnum, java.lang.CharSequence type, java.lang.CharSequence manufacturer, java.lang.CharSequence issue_date, java.lang.CharSequence model, java.lang.CharSequence status, java.lang.CharSequence aircraft_type, java.lang.CharSequence engine_type, java.lang.Integer year) {
    this.tailnum = tailnum;
    this.type = type;
    this.manufacturer = manufacturer;
    this.issue_date = issue_date;
    this.model = model;
    this.status = status;
    this.aircraft_type = aircraft_type;
    this.engine_type = engine_type;
    this.year = year;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return tailnum;
    case 1: return type;
    case 2: return manufacturer;
    case 3: return issue_date;
    case 4: return model;
    case 5: return status;
    case 6: return aircraft_type;
    case 7: return engine_type;
    case 8: return year;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: tailnum = (java.lang.CharSequence)value$; break;
    case 1: type = (java.lang.CharSequence)value$; break;
    case 2: manufacturer = (java.lang.CharSequence)value$; break;
    case 3: issue_date = (java.lang.CharSequence)value$; break;
    case 4: model = (java.lang.CharSequence)value$; break;
    case 5: status = (java.lang.CharSequence)value$; break;
    case 6: aircraft_type = (java.lang.CharSequence)value$; break;
    case 7: engine_type = (java.lang.CharSequence)value$; break;
    case 8: year = (java.lang.Integer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'tailnum' field.
   * @return The value of the 'tailnum' field.
   */
  public java.lang.CharSequence getTailnum() {
    return tailnum;
  }

  /**
   * Sets the value of the 'tailnum' field.
   * @param value the value to set.
   */
  public void setTailnum(java.lang.CharSequence value) {
    this.tailnum = value;
  }

  /**
   * Gets the value of the 'type' field.
   * @return The value of the 'type' field.
   */
  public java.lang.CharSequence getType() {
    return type;
  }

  /**
   * Sets the value of the 'type' field.
   * @param value the value to set.
   */
  public void setType(java.lang.CharSequence value) {
    this.type = value;
  }

  /**
   * Gets the value of the 'manufacturer' field.
   * @return The value of the 'manufacturer' field.
   */
  public java.lang.CharSequence getManufacturer() {
    return manufacturer;
  }

  /**
   * Sets the value of the 'manufacturer' field.
   * @param value the value to set.
   */
  public void setManufacturer(java.lang.CharSequence value) {
    this.manufacturer = value;
  }

  /**
   * Gets the value of the 'issue_date' field.
   * @return The value of the 'issue_date' field.
   */
  public java.lang.CharSequence getIssueDate() {
    return issue_date;
  }

  /**
   * Sets the value of the 'issue_date' field.
   * @param value the value to set.
   */
  public void setIssueDate(java.lang.CharSequence value) {
    this.issue_date = value;
  }

  /**
   * Gets the value of the 'model' field.
   * @return The value of the 'model' field.
   */
  public java.lang.CharSequence getModel() {
    return model;
  }

  /**
   * Sets the value of the 'model' field.
   * @param value the value to set.
   */
  public void setModel(java.lang.CharSequence value) {
    this.model = value;
  }

  /**
   * Gets the value of the 'status' field.
   * @return The value of the 'status' field.
   */
  public java.lang.CharSequence getStatus() {
    return status;
  }

  /**
   * Sets the value of the 'status' field.
   * @param value the value to set.
   */
  public void setStatus(java.lang.CharSequence value) {
    this.status = value;
  }

  /**
   * Gets the value of the 'aircraft_type' field.
   * @return The value of the 'aircraft_type' field.
   */
  public java.lang.CharSequence getAircraftType() {
    return aircraft_type;
  }

  /**
   * Sets the value of the 'aircraft_type' field.
   * @param value the value to set.
   */
  public void setAircraftType(java.lang.CharSequence value) {
    this.aircraft_type = value;
  }

  /**
   * Gets the value of the 'engine_type' field.
   * @return The value of the 'engine_type' field.
   */
  public java.lang.CharSequence getEngineType() {
    return engine_type;
  }

  /**
   * Sets the value of the 'engine_type' field.
   * @param value the value to set.
   */
  public void setEngineType(java.lang.CharSequence value) {
    this.engine_type = value;
  }

  /**
   * Gets the value of the 'year' field.
   * @return The value of the 'year' field.
   */
  public java.lang.Integer getYear() {
    return year;
  }

  /**
   * Sets the value of the 'year' field.
   * @param value the value to set.
   */
  public void setYear(java.lang.Integer value) {
    this.year = value;
  }

  /**
   * Creates a new PlanesDataAvroSchema RecordBuilder.
   * @return A new PlanesDataAvroSchema RecordBuilder
   */
  public static com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder newBuilder() {
    return new com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder();
  }

  /**
   * Creates a new PlanesDataAvroSchema RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new PlanesDataAvroSchema RecordBuilder
   */
  public static com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder newBuilder(com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder other) {
    return new com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder(other);
  }

  /**
   * Creates a new PlanesDataAvroSchema RecordBuilder by copying an existing PlanesDataAvroSchema instance.
   * @param other The existing instance to copy.
   * @return A new PlanesDataAvroSchema RecordBuilder
   */
  public static com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder newBuilder(com.training.pojos.generatedSources.PlanesDataAvroSchema other) {
    return new com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder(other);
  }

  /**
   * RecordBuilder for PlanesDataAvroSchema instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<PlanesDataAvroSchema>
    implements org.apache.avro.data.RecordBuilder<PlanesDataAvroSchema> {

    private java.lang.CharSequence tailnum;
    private java.lang.CharSequence type;
    private java.lang.CharSequence manufacturer;
    private java.lang.CharSequence issue_date;
    private java.lang.CharSequence model;
    private java.lang.CharSequence status;
    private java.lang.CharSequence aircraft_type;
    private java.lang.CharSequence engine_type;
    private int year;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.tailnum)) {
        this.tailnum = data().deepCopy(fields()[0].schema(), other.tailnum);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.type)) {
        this.type = data().deepCopy(fields()[1].schema(), other.type);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.manufacturer)) {
        this.manufacturer = data().deepCopy(fields()[2].schema(), other.manufacturer);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.issue_date)) {
        this.issue_date = data().deepCopy(fields()[3].schema(), other.issue_date);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.model)) {
        this.model = data().deepCopy(fields()[4].schema(), other.model);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.status)) {
        this.status = data().deepCopy(fields()[5].schema(), other.status);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.aircraft_type)) {
        this.aircraft_type = data().deepCopy(fields()[6].schema(), other.aircraft_type);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.engine_type)) {
        this.engine_type = data().deepCopy(fields()[7].schema(), other.engine_type);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.year)) {
        this.year = data().deepCopy(fields()[8].schema(), other.year);
        fieldSetFlags()[8] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing PlanesDataAvroSchema instance
     * @param other The existing instance to copy.
     */
    private Builder(com.training.pojos.generatedSources.PlanesDataAvroSchema other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.tailnum)) {
        this.tailnum = data().deepCopy(fields()[0].schema(), other.tailnum);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.type)) {
        this.type = data().deepCopy(fields()[1].schema(), other.type);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.manufacturer)) {
        this.manufacturer = data().deepCopy(fields()[2].schema(), other.manufacturer);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.issue_date)) {
        this.issue_date = data().deepCopy(fields()[3].schema(), other.issue_date);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.model)) {
        this.model = data().deepCopy(fields()[4].schema(), other.model);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.status)) {
        this.status = data().deepCopy(fields()[5].schema(), other.status);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.aircraft_type)) {
        this.aircraft_type = data().deepCopy(fields()[6].schema(), other.aircraft_type);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.engine_type)) {
        this.engine_type = data().deepCopy(fields()[7].schema(), other.engine_type);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.year)) {
        this.year = data().deepCopy(fields()[8].schema(), other.year);
        fieldSetFlags()[8] = true;
      }
    }

    /**
      * Gets the value of the 'tailnum' field.
      * @return The value.
      */
    public java.lang.CharSequence getTailnum() {
      return tailnum;
    }

    /**
      * Sets the value of the 'tailnum' field.
      * @param value The value of 'tailnum'.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder setTailnum(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.tailnum = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'tailnum' field has been set.
      * @return True if the 'tailnum' field has been set, false otherwise.
      */
    public boolean hasTailnum() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'tailnum' field.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder clearTailnum() {
      tailnum = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'type' field.
      * @return The value.
      */
    public java.lang.CharSequence getType() {
      return type;
    }

    /**
      * Sets the value of the 'type' field.
      * @param value The value of 'type'.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder setType(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.type = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'type' field has been set.
      * @return True if the 'type' field has been set, false otherwise.
      */
    public boolean hasType() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'type' field.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder clearType() {
      type = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'manufacturer' field.
      * @return The value.
      */
    public java.lang.CharSequence getManufacturer() {
      return manufacturer;
    }

    /**
      * Sets the value of the 'manufacturer' field.
      * @param value The value of 'manufacturer'.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder setManufacturer(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.manufacturer = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'manufacturer' field has been set.
      * @return True if the 'manufacturer' field has been set, false otherwise.
      */
    public boolean hasManufacturer() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'manufacturer' field.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder clearManufacturer() {
      manufacturer = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'issue_date' field.
      * @return The value.
      */
    public java.lang.CharSequence getIssueDate() {
      return issue_date;
    }

    /**
      * Sets the value of the 'issue_date' field.
      * @param value The value of 'issue_date'.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder setIssueDate(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.issue_date = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'issue_date' field has been set.
      * @return True if the 'issue_date' field has been set, false otherwise.
      */
    public boolean hasIssueDate() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'issue_date' field.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder clearIssueDate() {
      issue_date = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'model' field.
      * @return The value.
      */
    public java.lang.CharSequence getModel() {
      return model;
    }

    /**
      * Sets the value of the 'model' field.
      * @param value The value of 'model'.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder setModel(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.model = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'model' field has been set.
      * @return True if the 'model' field has been set, false otherwise.
      */
    public boolean hasModel() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'model' field.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder clearModel() {
      model = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'status' field.
      * @return The value.
      */
    public java.lang.CharSequence getStatus() {
      return status;
    }

    /**
      * Sets the value of the 'status' field.
      * @param value The value of 'status'.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder setStatus(java.lang.CharSequence value) {
      validate(fields()[5], value);
      this.status = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'status' field has been set.
      * @return True if the 'status' field has been set, false otherwise.
      */
    public boolean hasStatus() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'status' field.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder clearStatus() {
      status = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'aircraft_type' field.
      * @return The value.
      */
    public java.lang.CharSequence getAircraftType() {
      return aircraft_type;
    }

    /**
      * Sets the value of the 'aircraft_type' field.
      * @param value The value of 'aircraft_type'.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder setAircraftType(java.lang.CharSequence value) {
      validate(fields()[6], value);
      this.aircraft_type = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'aircraft_type' field has been set.
      * @return True if the 'aircraft_type' field has been set, false otherwise.
      */
    public boolean hasAircraftType() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'aircraft_type' field.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder clearAircraftType() {
      aircraft_type = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'engine_type' field.
      * @return The value.
      */
    public java.lang.CharSequence getEngineType() {
      return engine_type;
    }

    /**
      * Sets the value of the 'engine_type' field.
      * @param value The value of 'engine_type'.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder setEngineType(java.lang.CharSequence value) {
      validate(fields()[7], value);
      this.engine_type = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'engine_type' field has been set.
      * @return True if the 'engine_type' field has been set, false otherwise.
      */
    public boolean hasEngineType() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'engine_type' field.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder clearEngineType() {
      engine_type = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /**
      * Gets the value of the 'year' field.
      * @return The value.
      */
    public java.lang.Integer getYear() {
      return year;
    }

    /**
      * Sets the value of the 'year' field.
      * @param value The value of 'year'.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder setYear(int value) {
      validate(fields()[8], value);
      this.year = value;
      fieldSetFlags()[8] = true;
      return this;
    }

    /**
      * Checks whether the 'year' field has been set.
      * @return True if the 'year' field has been set, false otherwise.
      */
    public boolean hasYear() {
      return fieldSetFlags()[8];
    }


    /**
      * Clears the value of the 'year' field.
      * @return This builder.
      */
    public com.training.pojos.generatedSources.PlanesDataAvroSchema.Builder clearYear() {
      fieldSetFlags()[8] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PlanesDataAvroSchema build() {
      try {
        PlanesDataAvroSchema record = new PlanesDataAvroSchema();
        record.tailnum = fieldSetFlags()[0] ? this.tailnum : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.type = fieldSetFlags()[1] ? this.type : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.manufacturer = fieldSetFlags()[2] ? this.manufacturer : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.issue_date = fieldSetFlags()[3] ? this.issue_date : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.model = fieldSetFlags()[4] ? this.model : (java.lang.CharSequence) defaultValue(fields()[4]);
        record.status = fieldSetFlags()[5] ? this.status : (java.lang.CharSequence) defaultValue(fields()[5]);
        record.aircraft_type = fieldSetFlags()[6] ? this.aircraft_type : (java.lang.CharSequence) defaultValue(fields()[6]);
        record.engine_type = fieldSetFlags()[7] ? this.engine_type : (java.lang.CharSequence) defaultValue(fields()[7]);
        record.year = fieldSetFlags()[8] ? this.year : (java.lang.Integer) defaultValue(fields()[8]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<PlanesDataAvroSchema>
    WRITER$ = (org.apache.avro.io.DatumWriter<PlanesDataAvroSchema>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<PlanesDataAvroSchema>
    READER$ = (org.apache.avro.io.DatumReader<PlanesDataAvroSchema>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
