
jcrcache is a simple wrapper around an arbitrary PersistenceDelegate which caches values.  
It is intended to be used in the situation that the JCR is being accessed over RMI, which is very slow. 