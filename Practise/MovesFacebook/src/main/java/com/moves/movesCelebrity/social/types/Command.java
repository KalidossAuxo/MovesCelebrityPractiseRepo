package com.moves.movesCelebrity.social.types;

import java.util.concurrent.CompletableFuture;

public interface Command<K,V> {

    CompletableFuture<K> execute(V arg);

}
