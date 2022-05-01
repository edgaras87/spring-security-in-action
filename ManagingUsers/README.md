# Notes

## UserDetailsManager
		
interface UserDetailsManager extends UserDetailsService and adds more functionality:
  - void createUser(UserDetails user);
  - void updateUser(UserDetails user);
  - void deleteUser(String username);
  - void changePassword(String oldPassword, String newPassword);
  - boolean userExists(String username);

 JdbcUserDetailsManager implements UserDetailsManager


