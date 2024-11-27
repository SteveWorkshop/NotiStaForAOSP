package io.github.materialapps.notistar.entity;

import androidx.room.Entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
public class ChatRecord extends BaseEntity implements Serializable {
    private int operationType;
    private String responseText;
}
