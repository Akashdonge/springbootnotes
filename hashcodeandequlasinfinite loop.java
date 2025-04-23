Understanding StackOverflowError Due to @Data and hashCode/equals in Bidirectional Relationships

When using Lombok's @Data annotation in entities with bidirectional relationships, an infinite loop can occur due to parallel comparison of objects.

Why Does This Happen?

Hibernate saves employee using employeeRepo.save(employee);

Hibernate tries to persist changes and compute hashCode().

hashCode() in EmployeeEntity is called

Since @Data generates hashCode() and equals(), it includes all fields, including the DepartmentEntity reference.

hashCode() in DepartmentEntity is called

The department contains a list of employees, so it calls hashCode() on EmployeeEntity.

Infinite Loop Begins

EmployeeEntity.hashCode() calls DepartmentEntity.hashCode().

DepartmentEntity.hashCode() calls EmployeeEntity.hashCode() again.

This cycle continues indefinitely → StackOverflowError.

How to Fix This?

Manually implement hashCode() and equals()

Avoid including bidirectional references (department in EmployeeEntity and employees in DepartmentEntity).

Use @ToString.Exclude and @EqualsAndHashCode.Exclude

These annotations prevent infinite recursion by excluding the field from hashCode() and equals().

Use @JsonIgnore on one side (for Jackson serialization)

This avoids infinite recursion during JSON conversion.

Key Takeaway

Parallel comparison of objects happens recursively, leading to infinite loops. Manually controlling hashCode() and equals() prevents StackOverflowError and ensures Hibernate works correctly.
