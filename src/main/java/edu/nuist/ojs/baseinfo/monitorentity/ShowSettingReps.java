package edu.nuist.ojs.baseinfo.monitorentity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowSettingReps  extends JpaRepository<ShowSetting, Long> {
    public ShowSetting save(ShowSetting ss);

    public ShowSetting findByUid(long uid);
}
