package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity;

import com.taxidispatcher.modules.dispatcher.domain.model.CandidateStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "DISPATCH_CANDIDATE_DRIVERS")
@Entity
@Getter
@NoArgsConstructor
public class DispatchCandidateDriverJpaEntity {
    @EmbeddedId
    private DispatchCandidateDriverId id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CandidateStatus status;

    public DispatchCandidateDriverJpaEntity(DispatchCandidateDriverId id, CandidateStatus status) {
        this.id = id;
        this.status = status;
    }
}
