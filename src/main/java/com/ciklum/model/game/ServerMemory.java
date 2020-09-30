package com.ciklum.model.game;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The GameMemory is the memory for all the outcomes for all the different users.
 * For each userName, it contains the list of outcomes for all the games played from the 
 *  beggining of the GameServer creation.
 * 
 */
public class ServerMemory implements Closeable {
    
    private Map<String, List<RoundResult>> games = new ConcurrentHashMap<>(); // begin server with empty Concurrent HashMap
    
    private long totalRoundsPlayed = 0;
    private long totalWinsP1 = 0;
    private long totalWinsP2 = 0;
    private long totalDraws = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private ExecutorService executor = Executors.newFixedThreadPool(10);

    public ServerMemory() {
        // nothing here for now
    }

    /**
     * @return the roundsPlayed
     */
    public CompletableFuture<Integer> getRoundsPlayedForUser(String userName) {
        lock.readLock().lock();

        CompletableFuture<Integer> result =
            CompletableFuture.completedFuture(this.games.getOrDefault(userName, new ArrayList<>()).size());

        lock.readLock().unlock();
        
        return result;
    }

    /**
     * @return the rounds
     */
    public CompletableFuture<List<RoundResult>> getRounds(String userName) {
        lock.readLock().lock();

        CompletableFuture<List<RoundResult>> result =
            CompletableFuture.completedFuture(this.games.getOrDefault(userName, new ArrayList<>()));

        lock.readLock().unlock();
        
        return result;
    }

    /**
     * Add a round result "result" to the user "userName"
     * 
     * @param userName
     * @param result
     */
    public CompletableFuture<Void> addNewResult(String userName, RoundResult result) {
        return CompletableFuture.runAsync(() -> {
            lock.writeLock().lock();

            // take results for "userName" or start a new session for that user
            List<RoundResult> rounds = this.games.getOrDefault(userName, new ArrayList<>());

            rounds.add(result);
            this.games.put(userName, rounds);

            this.accumulateStatsForResult(result);

            lock.writeLock().unlock();

        }, executor);
    }

    /**
     * Private helper method for previous function of adding a new result.
     * It only accumulates the counters for all the needed statistics.
     * 
     * @param result
     */
    private void accumulateStatsForResult(RoundResult result) {
        this.totalRoundsPlayed++;

        if (result.isDraw()) {
            this.totalDraws++;
        } else if (result.isPlayer1Winner()) {
            this.totalWinsP1++;
        } else {
            this.totalWinsP2++;
        }
    }

    public CompletableFuture<GameStats> getGameStats() {
        lock.readLock().lock();

        CompletableFuture<GameStats> result =
           CompletableFuture.completedFuture(new GameStats(this.totalRoundsPlayed, this.totalWinsP1, totalWinsP2, totalDraws));

        lock.readLock().unlock();

        return result;
    }

    public CompletableFuture<Boolean> clear() {
        
        return CompletableFuture.runAsync(() -> {
            lock.writeLock().lock();

            this.games.clear();
            this.totalDraws = 0;
            this.totalRoundsPlayed = 0;
            this.totalWinsP1 = 0;
            this.totalWinsP2 = 0;

            lock.writeLock().unlock();

        }, executor).thenApply( voidResult -> true);
    }

    @Override
    public void close() {
        executor.shutdown();
    }
}
