package com.ducorreia.springrapier.core.data;

import java.io.Serializable;

public interface DataEntity extends Serializable {
    Integer getId();

    void setId(Integer id);

    String getHash();

    void setHash(String hash);

    Boolean getActive();

    void setActive(Boolean active);
}
