package kata.imad.numbertostring.exposition.rest;

import kata.imad.numbertostring.batch.NumberTransformationBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberTransformationBatchController {
    private final NumberTransformationBatch numberTransformationbatch;
    @Autowired
    public NumberTransformationBatchController(NumberTransformationBatch numberTransformationbatch) {
        this.numberTransformationbatch = numberTransformationbatch;
    }

    @GetMapping("/batchexecution")
    public ResponseEntity<String> getBatchExecution() {
        try {
            numberTransformationbatch.executeJob();
            return ResponseEntity.ok("Batch execution completed");
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Error executing batch"+e.getMessage());
        }
    }
}
