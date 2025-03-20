Note on Using Optional When an Object is Needed
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) { Optional<UserDto> user= userservice.getUser(id);
              return user.map(users->ResponseEntity.ok(users)).orElse(ResponseEntity.notFound().build());}

    public Optional<UserDto> getUser(Long id) {
        Optional<UserEntity> entity= Userrepo.findById(id);
        return entity.map(user->modelmapper.map(user,UserDto.class));
    }
When using Optional, sometimes you may need to return an actual object instead of Optional<T>.
In such cases, you can use orElse(null) to return an object or null if the Optional is empty.
Example: Returning an Object Instead of Optional<T>
✅ Using orElse(null)


public User getUser(Long id) {
    return userRepo.findById(id).orElse(null); // Returns User object or null
}
🔹 Behavior:

If the user exists → Returns User object.
If the user does not exist → Returns null.
When to Use orElse(null)?
🔹 Use it when the caller expects an object and can handle null.
🔹 Useful when working with legacy code that does not support Optional.

Alternative: Avoid null With orElseThrow()
Instead of returning null, you can throw an exception if the user is not found:

java
Copy
Edit
public User getUser(Long id) {
    return userRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
}
🔴 This avoids null values but throws an exception if no user exists.

Conclusion
✅ Use Optional<T> when possible to avoid null.
✅ If an object is needed instead of Optional<T>, use .orElse(null).
✅ For strict validation, prefer .orElseThrow(). 
