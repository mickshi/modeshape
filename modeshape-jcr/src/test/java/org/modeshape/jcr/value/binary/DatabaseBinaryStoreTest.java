/*
 * ModeShape (http://www.modeshape.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.modeshape.jcr.value.binary;

import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.modeshape.jcr.store.DataSourceConfig;

/**
 * @author kulikov
 */
public class DatabaseBinaryStoreTest extends AbstractBinaryStoreTest {

    private static final DataSourceConfig DB_CONFIG = new DataSourceConfig();
    private static DatabaseBinaryStore store;

    @BeforeClass
    public static void beforeClass() {
        String driver = DB_CONFIG.getDriverClassName();
        String url = DB_CONFIG.getUrl();
        String username = DB_CONFIG.getUsername();
        String password = DB_CONFIG.getPassword();
        store = new DatabaseBinaryStore(driver, url, username, password);
        store.start();
    }

    @AfterClass
    public static void afterClass() {
        store.shutdown();
    }

    @Override
    protected BinaryStore getBinaryStore() {
        return store;
    }

    @Override
    public void shouldStoreZeroLengthBinary() throws BinaryStoreException, IOException {
        if (DB_CONFIG.getDriverClassName().toLowerCase().contains("oracle")) {
            // Oracle does not store 0 sized byte arrays
            return;
        }
        super.shouldStoreZeroLengthBinary();
    }
}
