package com.saintdan.framework.repo;

import com.saintdan.framework.BaseTest;
import com.saintdan.framework.component.CustomPasswordEncoder;
import com.saintdan.framework.po.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Seed data.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/20/15
 * @since JDK1.8
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class Seed extends BaseTest {

  // --------------------------
  // SEED
  // --------------------------

  @Test
  public void test001() throws Exception {
    System.out.println("test001 start.");
    User user = userRepository.save(getRoot());
    ROOT_USER = user.getId();
    System.out.println("ROOT_USER is:" + ROOT_USER);
    System.out.println("test001 successfully.");
  }

  @Test
  public void test002() throws Exception {
    System.out.println("test002 start.");
    // Init client
    clientRepository.save(getClients());

    // Init user
    userRepository.save(getUsers());

    // Init role
    roleRepository.save(getRoles());

    // Init group
    groupRepository.save(getGroups());

    // Init resource
    resourceRepository.save(getResources());
    System.out.println("test002 successfully.");
  }

  @Test
  public void test003() throws Exception {
    System.out.println("test003 start.");
    userAndRole();
    roleAndGroup();
    groupAndResource();
    System.out.println("-----------------------------");
    System.out.println("-- Insert seed successful. --");
    System.out.println("-----------------------------");
    System.out.println("test003 successfully.");
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired
  private CustomPasswordEncoder passwordEncoder;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private ResourceRepository resourceRepository;

  private static final String ROOT = "root";
  private static final String ADMIN = "admin";
  private static final String GUEST = "guest";
  private static final String ACCOUNT = " account";
  private static final String ROLE = " role";

  private static Long ROOT_USER;

  /**
   * Get root.
   *
   * @return root
   */
  private User getRoot() {
    User root = new User();
    root.setUsr(ROOT);
    root.setPwd(passwordEncoder.encode(ROOT));
    root.setName(ROOT);
    root.setDescription(ROOT + ACCOUNT);
    root.setCreatedBy(0L);
    root.setLastModifiedBy(0L);
    return root;
  }

  /**
   * Get clients list.
   *
   * @return clients
   */
  private Iterable<Client> getClients() {
    List<Client> clients = new ArrayList<>();

    Client admin = new Client();
    admin.setClientIdAlias("admin");
    admin.setClientSecretAlias("123456");
    admin.setAuthorizedGrantTypeStr("password,refresh_token,authorization_code");
    admin.setScopeStr("read");
    admin.setResourceIdStr("api");
    admin.setCreatedBy(ROOT_USER);
    admin.setLastModifiedBy(ROOT_USER);
    admin.setRegisteredRedirectUriStr("http://github.com/saintdan");
    admin.setAccessTokenValiditySecondsAlias(1800);
    admin.setRefreshTokenValiditySecondsAlias(24 * 60 * 60);
    admin.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvpO6M1Ghv4YeEeOFHB41FtzwDLB49ovrjfYU4+YvTvXvwL1AdVlJhKfp/MveMK8tzL5Prya11nsIQnyz/dVdiWhu7xqC6fE/xbWswEskBRa/QUvOFaKZS6ZRenGsst7YTQmiEWlhZwduDvCcPrz4pEusRg+GtETdbWqO3D0O+NF9bmkEGcKvHB1BHKv6Nj8PSL0Zt8h2fbZLWNSEYWPLCA+onhtGL7pAkpGQxAtZLJTYhrTw4oo7+bcSjha/2AHfnsCcMa65EoU1BSjD18bjG+AAE6JNURH5Nl2NgRL7wT4LH1/0vJpUnCxjkWWN46648k22ogciDSr73msJuAzp9wIDAQAB");
    clients.add(admin);

    Client ios = new Client();
    ios.setClientIdAlias("ios_app");
    ios.setClientSecretAlias("123456");
    ios.setAuthorizedGrantTypeStr("password,refresh_token,authorization_code");
    ios.setScopeStr("read");
    ios.setResourceIdStr("api");
    ios.setCreatedBy(ROOT_USER);
    ios.setLastModifiedBy(ROOT_USER);
    ios.setRegisteredRedirectUriStr("http://www.apple.com");
    ios.setAccessTokenValiditySecondsAlias(1800);
    ios.setRefreshTokenValiditySecondsAlias(24 * 60 * 60);
    ios.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvpO6M1Ghv4YeEeOFHB41FtzwDLB49ovrjfYU4+YvTvXvwL1AdVlJhKfp/MveMK8tzL5Prya11nsIQnyz/dVdiWhu7xqC6fE/xbWswEskBRa/QUvOFaKZS6ZRenGsst7YTQmiEWlhZwduDvCcPrz4pEusRg+GtETdbWqO3D0O+NF9bmkEGcKvHB1BHKv6Nj8PSL0Zt8h2fbZLWNSEYWPLCA+onhtGL7pAkpGQxAtZLJTYhrTw4oo7+bcSjha/2AHfnsCcMa65EoU1BSjD18bjG+AAE6JNURH5Nl2NgRL7wT4LH1/0vJpUnCxjkWWN46648k22ogciDSr73msJuAzp9wIDAQAB");
    clients.add(ios);

    return clients;
  }

  /**
   * Get users list.
   *
   * @return users
   */
  private Iterable<User> getUsers() {
    List<User> users = new ArrayList<>();
    User admin = new User();
    User guest = new User();

    // Init admin user
    admin.setUsr(ADMIN);
    admin.setPwd(passwordEncoder.encode(ADMIN));
    admin.setName(ADMIN);
    admin.setDescription(ADMIN + ACCOUNT);
    admin.setCreatedBy(ROOT_USER);
    admin.setLastModifiedBy(ROOT_USER);

    // Init guest user
    guest.setUsr(GUEST);
    guest.setPwd(passwordEncoder.encode(GUEST));
    guest.setName(GUEST);
    guest.setDescription(GUEST + ACCOUNT);
    guest.setCreatedBy(ROOT_USER);
    guest.setLastModifiedBy(ROOT_USER);

    users.add(admin);
    users.add(guest);

    return users;
  }

  /**
   * Get roles list
   *
   * @return roles
   */
  private Iterable<Role> getRoles() {
    List<Role> roles = new ArrayList<>();

    // Init root role
    Role root = new Role(ROOT, ROOT + ROLE);
    root.setCreatedBy(ROOT_USER);
    root.setLastModifiedBy(ROOT_USER);

    // Init admin role
    Role admin = new Role(ADMIN, ADMIN + ROLE);
    admin.setCreatedBy(ROOT_USER);
    admin.setLastModifiedBy(ROOT_USER);

    // Init guest role
    Role guest = new Role(GUEST, GUEST + ROLE);
    guest.setCreatedBy(ROOT_USER);
    guest.setLastModifiedBy(ROOT_USER);

    roles.add(root);
    roles.add(admin);
    roles.add(guest);

    return roles;
  }

  /**
   * Get groups list
   *
   * @return groups
   */
  private Iterable<Group> getGroups() {
    List<Group> groups = new ArrayList<>();

    // init root control group
    Group root = new Group(ROOT, "root privileges group");
    root.setCreatedBy(ROOT_USER);
    root.setLastModifiedBy(ROOT_USER);

    // Init client control group
    Group client = new Group("client", "client privileges group");
    client.setCreatedBy(ROOT_USER);
    client.setLastModifiedBy(ROOT_USER);

    // Init user control group
    Group user = new Group("user", "user privileges group");
    user.setCreatedBy(ROOT_USER);
    user.setLastModifiedBy(ROOT_USER);

    // Init authority control  group
    Group authority = new Group("authority", "authority privileges group");
    authority.setCreatedBy(ROOT_USER);
    authority.setLastModifiedBy(ROOT_USER);

    // Init resource group
    Group resource = new Group("resource", "resource privileges group");
    resource.setCreatedBy(ROOT_USER);
    resource.setLastModifiedBy(ROOT_USER);

    // Init guest group
    Group guest = new Group("guest", "guest privileges group");
    guest.setCreatedBy(ROOT_USER);
    guest.setLastModifiedBy(ROOT_USER);

    groups.add(root);
    groups.add(client);
    groups.add(guest);
    groups.add(user);
    groups.add(authority);
    groups.add(resource);


    return groups;
  }

  /**
   * Get resources list
   *
   * @return resources
   */
  private Iterable<Resource> getResources() {
    List<Resource> resources = new ArrayList<>();
    Resource root = new Resource("root", "/.*", 10000, "all resources");
    root.setCreatedBy(ROOT_USER);
    root.setLastModifiedBy(ROOT_USER);

    Resource message = new Resource("message", "/messages", 10, "message resource");
    message.setCreatedBy(ROOT_USER);
    message.setLastModifiedBy(ROOT_USER);

    Resource welcome = new Resource("welcome", "/welcome", 1, "welcome resource");
    welcome.setCreatedBy(ROOT_USER);
    welcome.setLastModifiedBy(ROOT_USER);

    Resource client = new Resource("client", "/client", 10, "client resource");
    client.setCreatedBy(ROOT_USER);
    client.setLastModifiedBy(ROOT_USER);

    Resource user = new Resource("user", "/users", 10, "user resource");
    user.setCreatedBy(ROOT_USER);
    user.setLastModifiedBy(ROOT_USER);

    Resource role = new Resource("role", "/roles", 10, "role resource");
    role.setCreatedBy(ROOT_USER);
    role.setLastModifiedBy(ROOT_USER);

    Resource group = new Resource("group", "/groups", 10, "group resource");
    group.setCreatedBy(ROOT_USER);
    group.setLastModifiedBy(ROOT_USER);

    Resource resource = new Resource("resource", "/resources", 10, "resource resource");
    resource.setCreatedBy(ROOT_USER);
    resource.setLastModifiedBy(ROOT_USER);

    resources.add(root);
    resources.add(client);
    resources.add(message);
    resources.add(welcome);
    resources.add(user);
    resources.add(role);
    resources.add(group);
    resources.add(resource);

    return resources;
  }

  /**
   * Build relation between user and role.
   *
   * @throws Exception
   */
  private void userAndRole() throws Exception {
    // root user -- root role
    Set<Role> rootRoles = new HashSet<>();
    User rootAccount = userRepository.findByUsr(ROOT);
    Role rootRole = roleRepository.findByName(ROOT);
    rootRoles.add(rootRole);
    rootAccount.setRoles(rootRoles);
    userRepository.save(rootAccount);

    // admin user -- admin role
    Set<Role> adminRoles = new HashSet<>();
    User adminAccount = userRepository.findByUsr(ADMIN);
    Role adminRole = roleRepository.findByName(ADMIN);
    adminRoles.add(adminRole);
    adminAccount.setRoles(adminRoles);
    userRepository.save(adminAccount);

    // guest user -- guest role
    Set<Role> guestRoles = new HashSet<>();
    User guestAccount = userRepository.findByUsr(GUEST);
    Role guestRole = roleRepository.findByName(GUEST);
    guestRoles.add(guestRole);
    guestAccount.setRoles(guestRoles);
    userRepository.save(guestAccount);
  }

  /**
   * Build relation between role and group.
   *
   * @throws Exception
   */
  private void roleAndGroup() throws Exception {
    // root role -- root group
    Set<Group> rootGroups = new HashSet<>();
    Role rootRole = roleRepository.findByName(ROOT);
    Group rootGroup = groupRepository.findByName(ROOT);
    rootGroups.add(rootGroup);
    rootRole.setGroups(rootGroups);
    roleRepository.save(rootRole);

    // admin role -- user, authority, resource, guest group
    Set<Group> adminGroups = new HashSet<>();
    Role adminRole = roleRepository.findByName(ADMIN);
    Group clientGroup = groupRepository.findByName("client");
    Group userGroup = groupRepository.findByName("user");
    Group authGroup = groupRepository.findByName("authority");
    Group resourceGroup = groupRepository.findByName("resource");
    Group guestGroup = groupRepository.findByName(GUEST);
    adminGroups.add(clientGroup);
    adminGroups.add(userGroup);
    adminGroups.add(authGroup);
    adminGroups.add(resourceGroup);
    adminGroups.add(guestGroup);
    adminRole.setGroups(adminGroups);
    roleRepository.save(adminRole);

    // guest role -- guest, message group
    Set<Group> guestGroups = new HashSet<>();
    Role guestRole = roleRepository.findByName(GUEST);
    guestGroups.add(guestGroup);
    guestRole.setGroups(guestGroups);
    roleRepository.save(guestRole);
  }

  /**
   * Build relation between group and resource.
   *
   * @throws Exception
   */
  private void groupAndResource() throws Exception {
    // root group -- root resource
    Set<Resource> rootResources = new HashSet<>();
    Group rootGroup = groupRepository.findByName(ROOT);
    Resource rootResource = resourceRepository.findByName(ROOT);
    rootResources.add(rootResource);
    rootGroup.setResources(rootResources);
    groupRepository.save(rootGroup);

    // client group -- client resource
    Set<Resource> clientResources = new HashSet<>();
    Group clientGroup = groupRepository.findByName("client");
    Resource clientResource = resourceRepository.findByName("client");
    clientResources.add(clientResource);
    clientGroup.setResources(clientResources);
    groupRepository.save(clientGroup);

    // guest group -- guest resource
    Set<Resource> guestResources = new HashSet<>();
    Group guestGroup = groupRepository.findByName(GUEST);
    Resource welcomeResource = resourceRepository.findByName("welcome");
    Resource messageResource = resourceRepository.findByName("message");
    guestResources.add(welcomeResource);
    guestResources.add(messageResource);
    guestGroup.setResources(guestResources);
    groupRepository.save(guestGroup);

    // user group -- user resource
    Set<Resource> userResources = new HashSet<>();
    Group userGroup = groupRepository.findByName("user");
    Resource userResource = resourceRepository.findByName("user");
    userResources.add(userResource);
    userGroup.setResources(userResources);
    groupRepository.save(userGroup);

    // authority group -- role, group resource
    Set<Resource> authResources = new HashSet<>();
    Group authGroup = groupRepository.findByName("authority");
    Resource roleResource = resourceRepository.findByName("role");
    Resource groupResource = resourceRepository.findByName("group");
    authResources.add(roleResource);
    authResources.add(groupResource);
    authGroup.setResources(authResources);
    groupRepository.save(authGroup);

    // resource group -- resource resource
    Set<Resource> resResources = new HashSet<>();
    Group resourceGroup = groupRepository.findByName("resource");
    Resource resourceResource = resourceRepository.findByName("resource");
    resResources.add(resourceResource);
    resourceGroup.setResources(resResources);
    groupRepository.save(resourceGroup);
  }
}
