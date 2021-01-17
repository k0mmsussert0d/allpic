import { Generic, Media, Image, Content } from "rbx";
import { CommentDTO } from "../../types/API";

const Comments = ({ list }: CommentsProps) => {

  const parseExactDate = (date: string): string => {
    return new Date(date).toLocaleTimeString(
      'en-gb',
      {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric',
        second: 'numeric'
      }
    );
  }

  return (
    <Generic as="section" className="comments">
      {list.map((comment: CommentDTO) => {
        return (
          <Media key={comment.id}>
            <Media.Item as="figure" align="left">
              <Image.Container as="p" size={64}>
                <Image
                  alt="64x64"
                  src="https://bulma.io/images/placeholders/128x128.png"
                />
              </Image.Container>
            </Media.Item>
            <Media.Item align="content">
              <Content>
                <p>
                  <strong>{comment.author}</strong>{" on "}
                  <small>{parseExactDate(comment.timeAdded)}</small>
                  <br />
                  {comment.message}
                </p>
              </Content>
            </Media.Item>
          </Media>
        );
      })}
    </Generic>
  );
};

export interface CommentsProps {
  list: Array<CommentDTO>
}

export default Comments;
