package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.exceptions.MembershipException;
import edu.sjsu.cmpe275.cartpool.exceptions.PoolNotFoundException;
import edu.sjsu.cmpe275.cartpool.exceptions.UserNotFoundException;
import edu.sjsu.cmpe275.cartpool.pojos.Pool;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
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

    @Transactional
    @Override
    public Pool save(Pool pool) {
        return poolRepository.save(pool);
    }

    @Transactional
    @Override
    public void delete(Long poolId) {
        Pool pool = poolRepository.findById(poolId).orElseThrow(() -> new PoolNotFoundException());
        Pooler poolLeader = pool.getPoolLeader();
        if (pool.getMembers().size() == 1 && pool.getMembers().contains(poolLeader)) {
            poolLeader.setPool(null);
            poolRepository.delete(pool);
        } else
            throw new MembershipException("Unable to delete pool!! :: Members exist in pool");
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

        Pooler referencePooler;
        if (screenName.equals("pool_leader_reference")) {
            referencePooler = pool.getPoolLeader();
            screenName = referencePooler.getScreenname();
        } else
            referencePooler = poolerRepository.findByScreenname(screenName);

        Pooler pooler = poolerRepository.findById(poolerId).orElseThrow(() -> new PoolNotFoundException());
        if(pool.getMembers().contains(pooler)){
            return "{\"message\": \"You are already a member of this pool\"}";
        }

        if(pool.getMembers().size() >= 4){
            return "{\"message\": \"Pool is full!! Only 4 members allowed in one pool\"}";
            //throw new MembershipException("Pool is full!! Only 4 members allowed in one pool");
        }

        if (referencePooler == null)
            throw new UserNotFoundException();

        for (Pooler member : pool.getMembers()) {
            if (member.getScreenname().equals(screenName)) {

                /////// send email to the reference pooler /////////

                String messageBody = "";
                URI uri = null;
                URL url = null;

                URI rejectPathUri = null;
                URL rejectPathUrl = null;

                try {
                    String protocol = "http";
                    String host = Constants.HOSTNAME;
                    int port = 8080;
                    String verifyPath = "/pool/verify/" + poolerId + "/" + poolId;
                    String rejectPath = "/pool/reject/" + poolerId + "/" + poolId;
                    uri = new URI(protocol, null, host, port, verifyPath, null, null);
                    url = uri.toURL();

                    rejectPathUri = new URI(protocol, null, host, port, rejectPath, null, null);
                    rejectPathUrl = rejectPathUri.toURL();
                    messageBody = "<h3>Take the action to accept or reject the membership request</h3>\n" +
                            " <a target='_blank' href=" + url + "><button style=\"background-color:#4CAF50\">Accept</button></a>\n" +
                            "<a target='_blank' href=" + rejectPathUrl + "><button style=\"background-color:#f44336\">Reject</button></a>";

                } catch (Exception e) {
                    e.printStackTrace();
                }

                emailService.sendEmailForPoolMembership(referencePooler.getEmail(),
                        "verification for pool membership", messageBody);

            }
        }
        return "{\"message\": \"verification email has been sent to reference pooler\"}";
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
        return poolRepository.save(pool);
    }

    @Transactional
    @Override
    public String reject(Long poolerId, Long poolId) {
        Pooler pooler = poolerRepository.findById(poolerId).orElseThrow(() -> new UserNotFoundException());
        Pool pool = poolRepository.findById(poolId).orElseThrow(() -> new PoolNotFoundException());

        String messageBody = "Reference Pooler has rejected your join request for pool: " + pool.getName();
        emailService.sendEmailForPoolMembership(pooler.getEmail(),
                "Rejection for pool membership", messageBody);
        return pooler.getFirstName() + "'s" + "join request is rejected!";
    }
}
