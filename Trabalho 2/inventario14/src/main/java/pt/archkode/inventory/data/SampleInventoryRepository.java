package pt.archkode.inventory.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SampleInventoryRepository
        extends
        JpaRepository<SampleInventory, Long>,
        JpaSpecificationExecutor<SampleInventory> {

}
