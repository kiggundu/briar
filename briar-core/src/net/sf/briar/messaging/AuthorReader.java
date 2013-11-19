package net.sf.briar.messaging;

import static net.sf.briar.api.AuthorConstants.MAX_AUTHOR_NAME_LENGTH;
import static net.sf.briar.api.AuthorConstants.MAX_PUBLIC_KEY_LENGTH;
import static net.sf.briar.api.messaging.Types.AUTHOR;

import java.io.IOException;

import net.sf.briar.api.Author;
import net.sf.briar.api.AuthorId;
import net.sf.briar.api.crypto.CryptoComponent;
import net.sf.briar.api.crypto.MessageDigest;
import net.sf.briar.api.serial.DigestingConsumer;
import net.sf.briar.api.serial.Reader;
import net.sf.briar.api.serial.StructReader;

class AuthorReader implements StructReader<Author> {

	private final MessageDigest messageDigest;

	AuthorReader(CryptoComponent crypto) {
		messageDigest = crypto.getMessageDigest();
	}

	public Author readStruct(Reader r) throws IOException {
		// Set up the reader
		DigestingConsumer digesting = new DigestingConsumer(messageDigest);
		r.addConsumer(digesting);
		// Read and digest the data
		r.readStructStart(AUTHOR);
		String name = r.readString(MAX_AUTHOR_NAME_LENGTH);
		byte[] publicKey = r.readBytes(MAX_PUBLIC_KEY_LENGTH);
		r.readStructEnd();
		// Reset the reader
		r.removeConsumer(digesting);
		// Build and return the author
		AuthorId id = new AuthorId(messageDigest.digest());
		return new Author(id, name, publicKey);
	}
}
