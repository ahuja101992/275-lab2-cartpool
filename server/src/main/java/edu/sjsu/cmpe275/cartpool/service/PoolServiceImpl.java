package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.exceptions.MembershipException;
import edu.sjsu.cmpe275.cartpool.exceptions.PoolNotFoundException;
import edu.sjsu.cmpe275.cartpool.exceptions.UserNotFoundException;
import edu.sjsu.cmpe275.cartpool.pojos.Orders;
import edu.sjsu.cmpe275.cartpool.pojos.Pool;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.repository.OrderRepository;
import edu.sjsu.cmpe275.cartpool.repository.PoolRepository;
import edu.sjsu.cmpe275.cartpool.repository.PoolerRepository;
import edu.sjsu.cmpe275.cartpool.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URL;
import java.util.List;

@Service
public class PoolServiceImpl implements PoolService {

    @Autowired
    private PoolRepository<Pool> poolRepository;

    @Autowired
    private PoolerRepository<Pooler> poolerRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OrderRepository<Orders> orderRepository;

    @Transactional
    @Override
    public Pool save(Pool pool) {
        return poolRepository.save(pool);
    }

    @Transactional
    @Override
    public String delete(Long poolId) {
        Pool pool = poolRepository.findById(poolId).orElseThrow(() -> new PoolNotFoundException());
        Pooler poolLeader = pool.getPoolLeader();

        List<Orders> activeOrders = orderRepository.findByPoolAndStatus(pool, "Placed");
        if(activeOrders.size() > 0){
            return "Unable to delete pool!! :: Active Orders in pool exists";
        }

        if (pool.getMembers().size() == 1 && pool.getMembers().contains(poolLeader)) {
            poolLeader.setPool(null);
            poolRepository.delete(pool);
            return "Deleted pool successfully";
        } else {
            return "Unable to delete pool!! :: Members exist in pool";
            //throw new MembershipException("Unable to delete pool!! :: Members exist in pool");
        }
    }

    @Transactional
    @Override
    public boolean chceckMembership(Long poolerId) {
        Pooler pooler = poolerRepository.findById(poolerId).orElseThrow(() -> new UserNotFoundException());
        return pooler.getPool() == null;
    }

    @Transactional
    @Override
    public List<Pool> searchPool(String searchParam) {
        List<Pool> poolList = poolRepository.findByNameOrNeighborhoodNameOrZipAllIgnoreCase(searchParam, searchParam, searchParam);
        return poolList;
    }

    @Transactional
    @Override
    public String joinPool(Long poolId, Long poolerId, String screenName) {
        Pool pool = poolRepository.findById(poolId).orElseThrow(() -> new PoolNotFoundException());

        Pooler pooler = poolerRepository.findById(poolerId).orElseThrow(() -> new PoolNotFoundException());
        if(pool.getMembers().contains(pooler)){
            return "{\"message\": \"You are already a member of this pool\"}";
        }

        if(pool.getMembers().size() >= 4){
            return "{\"message\": \"Pool is full!! Only 4 members allowed in one pool\"}";
            //throw new MembershipException("Pool is full!! Only 4 members allowed in one pool");
        }


        Pooler referencePooler;
        if (screenName.equals("pool_leader_reference")) {
            referencePooler = pool.getPoolLeader();
            //// send mail to pool leader
            String messageBody = "";
            URI uri = null;
            URL url = null;

            URI rejectPathUri = null;
            URL rejectPathUrl = null;
            try {
                String protocol = "http";
                String host = Constants.HOSTNAME;
                int port = 3000;
                String verifyPath = "/pool/verify/byPoolLeader/" + poolerId + "/" + poolId;
                String rejectPath = "/pool/reject/byPoolLeader/" + poolerId + "/" + poolId;
                uri = new URI(protocol, null, host, port, verifyPath, null, null);
                url = uri.toURL();

                rejectPathUri = new URI(protocol, null, host, port, rejectPath, null, null);
                rejectPathUrl = rejectPathUri.toURL();
                messageBody = "<script>console.log('hello')</script><h3>Take the action to accept or reject the membership request for pooler:  "+
                        pooler.getFirstName() + " " + pooler.getLastName()+"</h3>\n" +
                        " <a href=" + url + "><button style=\"background-color:#4CAF50\">Accept</button></a>\n" +
                        "<a href=" + rejectPathUrl + "><button style=\"background-color:#f44336\">Reject</button></a>";

            } catch (Exception e) {
                e.printStackTrace();
            }

            emailService.sendEmailForPoolMembership(referencePooler.getEmail(),
                    "verification for pool membership", messageBody);
            //screenName = referencePooler.getScreenname();

            return "{\"message\": \"verification email has been sent to pooler leader\"}";
        }
        else{
            referencePooler = poolerRepository.findByScreenname(screenName);
            if (referencePooler == null)
                throw new UserNotFoundException();

            String messageBody = "";
            URI uri = null;
            URL url = null;

            URI rejectPathUri = null;
            URL rejectPathUrl = null;

            try {
                String protocol = "http";
                String host = Constants.HOSTNAME;
                int port = 3000;
                String verifyPath = "/pool/support/byPooler/" + poolerId + "/" + poolId;
                String rejectPath = "/pool/reject/byPooler/" + poolerId + "/" + poolId;
                uri = new URI(protocol, null, host, port, verifyPath, null, null);
                url = uri.toURL();

                rejectPathUri = new URI(protocol, null, host, port, rejectPath, null, null);
                rejectPathUrl = rejectPathUri.toURL();
                messageBody = "<h3>Take the action to support or reject the membership request for pooler: " + pooler.getFirstName() + " " + pooler.getLastName()
                        + "</h3>\n" +
                        " <a href=" + url + "><button style=\"background-color:#4CAF50\">Support</button></a>\n" +
                        "<a href=" + rejectPathUrl + "><button style=\"background-color:#f44336\">Reject</button></a>";

                emailService.sendEmailForPoolMembership(referencePooler.getEmail(),
                        "verification for pool membership", messageBody);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "{\"message\": \"verification email has been sent to reference pooler\"}";
        }
    }

    @Transactional
    @Override
    public Pool verify(Long poolerId, Long poolId) {
        Pooler pooler = poolerRepository.findById(poolerId).orElseThrow(() -> new UserNotFoundException());
        Pool pool = poolRepository.findById(poolId).orElseThrow(() -> new PoolNotFoundException());

        pooler.setVerifiedForPoolMembership(true);
        pool.addPooler(pooler);
        pooler.setPool(pool);
        poolerRepository.save(pooler);

        String messageBody = "Pool Leader has accepted your join request for pool:  " + pool.getName();
        emailService.sendEmailForPoolMembership(pooler.getEmail(),
                "pool membership request accepted by pool leader", messageBody);

        return poolRepository.save(pool);
    }

    @Transactional
    @Override
    public void reject(Long poolerId, Long poolId) {
        Pooler pooler = poolerRepository.findById(poolerId).orElseThrow(() -> new UserNotFoundException());
        Pool pool = poolRepository.findById(poolId).orElseThrow(() -> new PoolNotFoundException());

        String messageBody = "Pool Leader has rejected your join request for pool: " + pool.getName();
        emailService.sendEmailForPoolMembership(pooler.getEmail(),
                "Rejection for pool membership", messageBody);
        //return pooler.getFirstName() + "'s " + "join request is rejected!";
    }

    @Transactional
    @Override
    public void verifyByPooler(Long poolerId, Long poolId) {
        Pooler pooler = poolerRepository.findById(poolerId).orElseThrow(() -> new UserNotFoundException());
        Pool pool = poolRepository.findById(poolId).orElseThrow(() -> new PoolNotFoundException());

//        pooler.setVerifiedForPoolMembership(true);
//        pool.addPooler(pooler);
//        pooler.setPool(pool);

        String messageBody = "Reference Pooler has supported your join request for pool: " + pool.getName() + "\n";
        messageBody += "Verification mail has been sent to pool leader now";
        emailService.sendEmailForPoolMembership(pooler.getEmail(),
                "pool membership request verified by reference pooler", messageBody);

        //////     send mail to leader now  /////
       // String messageBody = "";
        URI uri = null;
        URL url = null;

        URI rejectPathUri = null;
        URL rejectPathUrl = null;
        try {
            String protocol = "http";
            String host = Constants.HOSTNAME;
            int port = 3000;
            String verifyPath = "/pool/verify/byPoolLeader/" + poolerId + "/" + poolId;
            String rejectPath = "/pool/reject/byPoolLeader/" + poolerId + "/" + poolId;
            uri = new URI(protocol, null, host, port, verifyPath, null, null);
            url = uri.toURL();

            rejectPathUri = new URI(protocol, null, host, port, rejectPath, null, null);
            rejectPathUrl = rejectPathUri.toURL();
            messageBody = "Reference Pooler has supported join request of " + pooler.getFirstName() + " " + pooler.getLastName() + " for pool: " + pool.getName() + "\n";
            messageBody += "<h3>Take the action to accept or reject the membership request</h3>\n" +
                    " <a href=" + url + "><button style=\"background-color:#4CAF50\">Accept</button></a>\n" +
                    "<a href=" + rejectPathUrl + "><button style=\"background-color:#f44336\">Reject</button></a>";

        } catch (Exception e) {
            e.printStackTrace();
        }

        emailService.sendEmailForPoolMembership(pool.getPoolLeader().getEmail(),
                "verification for pool membership", messageBody);
        //screenName = referencePooler.getScreenname();

//        String message = "Reference Pooler has supported your join request for pool: " + pool.getName() + "\n";
//        emailService.sendEmailForPoolMembership(pool.getPoolLeader().getEmail(),
//                "membership request verification", message);

        //return poolRepository.save(pool);
    }


    @Transactional
    @Override
    public void rejectByPooler(Long poolerId, Long poolId) {
        Pooler pooler = poolerRepository.findById(poolerId).orElseThrow(() -> new UserNotFoundException());
        Pool pool = poolRepository.findById(poolId).orElseThrow(() -> new PoolNotFoundException());

        String messageBody = "Reference Pooler has rejected your join request for pool: " + pool.getName();
        emailService.sendEmailForPoolMembership(pooler.getEmail(),
                "Rejection for pool membership", messageBody);
        //return pooler.getFirstName() + "'s " + "join request is rejected!";
    }
    @Transactional
    @Override
    public Long getLeader(Long poolId) {
        Pool pool = poolRepository.findById(poolId).orElseThrow(() -> new PoolNotFoundException());
        Pooler leader = pool.getPoolLeader();
        return leader.getId();
    }

    @Transactional
    @Override
    public Pool updatePool(Long poolId, String name, String neighborhoodName, String description){
        Pool pool = poolRepository.findById(poolId).orElseThrow(() -> new PoolNotFoundException());
        pool.setName(name);
        pool.setNeighborhoodName(neighborhoodName);
        pool.setDescription(description);

        return poolRepository.save(pool);
    }
}
