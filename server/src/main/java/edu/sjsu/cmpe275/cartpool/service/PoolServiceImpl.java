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
import java.net.URLEncoder;
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
        if (pool.getMembers().size() == 1 && pool.getMembers().get(0) == poolLeader) {
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
    public void joinPool(Long poolId, Long poolerId, String screenName) {

        Pooler referencePooler = poolerRepository.findByScreenname(screenName);
        if (referencePooler == null)
            throw new UserNotFoundException();

        Pool pool = poolRepository.findById(poolId).orElseThrow(() -> new PoolNotFoundException());

        for(Pooler member: pool.getMembers()){
            if(member.getScreenname() == screenName){

                /////// send email to the reference pooler /////////

                String messageBody = "";
                URI uri = null;
                URL url = null;

                try {
                    String protocol = "http";
                    String host = Constants.HOSTNAME;
                    int port = 8080;
                    String path = "/pool/verify/" + poolerId + "/" + poolId;

                    messageBody = "<h2>Perform the action </h2>" +
                            "<h3> Plaese click on the button to reset password \n </h3> "+
                            " <a target='_blank' href="+path+"><button>Accept</button></a>";
                    uri = new URI(protocol, null, host, port, path, null, null);
                    url = uri.toURL();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                emailService.sendVerificationEmail(referencePooler.getEmail(),
                        "CartPoll account verification for pool membership",
                        String.format("Please take action for pooler's membership " +
                                " clicking on the following link - %s", messageBody));
                //emailService.sendVerificationEmail(to, subject, text);
            }
        }

        ///// throw exception -> no pooler with given ScreenName found
        //return null;
    }

    @Override
    public Pool verify(Long poolerId, Long poolId) {
        Pooler pooler = poolerRepository.findById(poolerId).orElseThrow(() -> new UserNotFoundException());
        Pool pool = poolRepository.findById(poolId).orElseThrow(() -> new PoolNotFoundException());

        pooler.setVerifiedForPoolMembership(true);
        pool.addPooler(pooler);
        poolerRepository.save(pooler);
        return poolRepository.save(pool);
        //return poolRepository.save()
    }
}
