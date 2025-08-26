package com.taxidispatcher.modules.driver.adapter.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class DriverWorkGeoJpaId implements Serializable {
    @Column(name = "seq", nullable = false, updatable = false)
    private Long seq;

    /**
     * @see DriverWorkHistoryJpaEntity#getWorkHistoryId()
     */
    @JoinColumn(name = "work_history_id", nullable = false, updatable = false)
    private UUID workHistoryId;
}
