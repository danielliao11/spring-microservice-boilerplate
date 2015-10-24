package com.saintdan.framework.repo;

import com.saintdan.framework.Application;
import com.saintdan.framework.component.CustomPasswordEncoder;
import com.saintdan.framework.constant.VersionConstant;
import com.saintdan.framework.po.Group;
import com.saintdan.framework.po.Resource;
import com.saintdan.framework.po.Role;
import com.saintdan.framework.po.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Seed data.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/20/15
 * @since JDK1.8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class Seed {

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

    /**
     * Init database.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {

        // Clear database;
        userRepository.deleteAll();
        roleRepository.deleteAll();
        groupRepository.deleteAll();
        resourceRepository.deleteAll();

        // Init user
        userRepository.save(getUsers());

        // Init role
        roleRepository.save(getRoles());

        // Init group
        groupRepository.save(getGroups());

        // Init resource
        resourceRepository.save(getResources());
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
        Group userGroup = groupRepository.findByName("user");
        Group authGroup = groupRepository.findByName("authority");
        Group resourceGroup = groupRepository.findByName("resource");
        Group guestGroup = groupRepository.findByName(GUEST);
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
    public void groupAndResource() throws Exception {
        // root group -- root resource
        Set<Resource> rootResources = new HashSet<>();
        Group rootGroup = groupRepository.findByName(ROOT);
        Resource rootResource = resourceRepository.findByName(ROOT);
        rootResources.add(rootResource);
        rootGroup.setResources(rootResources);
        groupRepository.save(rootGroup);

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

    @Test
    public void testSeed() throws Exception {
        userAndRole();
        roleAndGroup();
        groupAndResource();
        System.out.println("-----------------------------");
        System.out.println("-- Insert seed successful. --");
        System.out.println("-----------------------------");
    }

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    /**
     * Get users list.
     *
     * @return      users
     */
    private Iterable<User> getUsers() {
        List<User> users = new ArrayList<>();
        User root = new User();
        User admin = new User();
        User guest = new User();

        // Init root user
        root.setUsr(ROOT);
        root.setPwd(passwordEncoder.encode(ROOT));
        root.setName(ROOT);
        root.setDescription(ROOT + ACCOUNT);
        root.setVersion(VersionConstant.INIT_VERSION);

        // Init admin user
        admin.setUsr(ADMIN);
        admin.setPwd(passwordEncoder.encode(ADMIN));
        admin.setName(ADMIN);
        admin.setDescription(ADMIN + ACCOUNT);
        admin.setVersion(VersionConstant.INIT_VERSION);

        // Init guest user
        guest.setUsr(GUEST);
        guest.setPwd(passwordEncoder.encode(GUEST));
        guest.setName(GUEST);
        guest.setDescription(GUEST + ACCOUNT);
        guest.setVersion(VersionConstant.INIT_VERSION);

        users.add(root);
        users.add(admin);
        users.add(guest);

        return users;
    }

    /**
     * Get roles list
     *
     * @return      roles
     */
    private Iterable<Role> getRoles() {
        List<Role> roles = new ArrayList<>();

        // Init root role
        Role root = new Role(ROOT, ROOT + ROLE);
        root.setVersion(VersionConstant.INIT_VERSION);

        // Init admin role
        Role admin = new Role(ADMIN, ADMIN + ROLE);
        admin.setVersion(VersionConstant.INIT_VERSION);

        // Init guest role
        Role guest = new Role(GUEST, GUEST + ROLE);
        guest.setVersion(VersionConstant.INIT_VERSION);

        roles.add(root);
        roles.add(admin);
        roles.add(guest);

        return roles;
    }

    /**
     * Get groups list
     *
     * @return      groups
     */
    private Iterable<Group> getGroups() {
        List<Group> groups = new ArrayList<>();

        // init root control group
        Group root = new Group(ROOT, "root rights group");
        root.setLastModifyAT(new Date());
        root.setCreateAT(new Date());
        root.setVersion(VersionConstant.INIT_VERSION);

        // Init user control group
        Group user = new Group("user", "user rights group");
        user.setLastModifyAT(new Date());
        user.setCreateAT(new Date());
        user.setVersion(VersionConstant.INIT_VERSION);

        // Init authority control  group
        Group authority = new Group("authority", "authority rights group");
        authority.setLastModifyAT(new Date());
        authority.setCreateAT(new Date());
        authority.setVersion(VersionConstant.INIT_VERSION);

        // Init resource group
        Group resource = new Group("resource", "resource rights group");
        resource.setLastModifyAT(new Date());
        resource.setCreateAT(new Date());
        resource.setVersion(VersionConstant.INIT_VERSION);

        // Init guest group
        Group guest = new Group("guest", "guest rights group");
        guest.setLastModifyAT(new Date());
        guest.setCreateAT(new Date());
        guest.setVersion(VersionConstant.INIT_VERSION);

        groups.add(root);
        groups.add(guest);
        groups.add(user);
        groups.add(authority);
        groups.add(resource);


        return groups;
    }

    /**
     * Get resources list
     *
     * @return      resources
     */
    private Iterable<Resource> getResources() {
        List<Resource> resources = new ArrayList<>();
        Resource root = new Resource("root", "/.*", 10000, "all resources");
        root.setLastModifyAT(new Date());
        root.setCreateAT(new Date());
        root.setVersion(VersionConstant.INIT_VERSION);

        Resource message = new Resource("message", "/messages", 10, "message resource");
        message.setLastModifyAT(new Date());
        message.setCreateAT(new Date());
        message.setVersion(VersionConstant.INIT_VERSION);

        Resource welcome = new Resource("welcome", "/welcome", 1, "welcome resource");
        welcome.setLastModifyAT(new Date());
        welcome.setCreateAT(new Date());
        welcome.setVersion(VersionConstant.INIT_VERSION);

        Resource user = new Resource("user", "/users" , 10, "user resource");
        user.setLastModifyAT(new Date());
        user.setCreateAT(new Date());
        user.setVersion(VersionConstant.INIT_VERSION);

        Resource role = new Resource("role", "/roles", 10, "role resource");
        role.setLastModifyAT(new Date());
        role.setCreateAT(new Date());
        role.setVersion(VersionConstant.INIT_VERSION);

        Resource group = new Resource("group", "/groups", 10, "group resource");
        group.setLastModifyAT(new Date());
        group.setCreateAT(new Date());
        group.setVersion(VersionConstant.INIT_VERSION);

        Resource resource = new Resource("resource", "/resources", 10, "resource resource");
        resource.setLastModifyAT(new Date());
        resource.setCreateAT(new Date());
        resource.setVersion(VersionConstant.INIT_VERSION);

        resources.add(root);
        resources.add(message);
        resources.add(welcome);
        resources.add(user);
        resources.add(role);
        resources.add(group);
        resources.add(resource);

        return resources;
    }
}
